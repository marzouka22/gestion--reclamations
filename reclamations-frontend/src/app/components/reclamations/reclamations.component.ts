import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Reclamation } from '../../models/reclamation.model';
import { Client } from '../../models/client.model';
import { AgentSAV } from '../../models/agent-sav.model';
import { ReclamationService } from '../../services/reclamation.service';
import { ClientService } from '../../services/client.service';
import { AgentSavService } from '../../services/agent-sav.service';

declare var bootstrap: any;

@Component({
  selector: 'app-reclamations',
  templateUrl: './reclamations.component.html'
})
export class ReclamationsComponent implements OnInit {
  reclamations: Reclamation[] = [];
  filtered: Reclamation[] = [];
  clients: Client[] = [];
  agents: AgentSAV[] = [];
  form!: FormGroup;
  affectForm!: FormGroup;
  statutForm!: FormGroup;
  noteForm!: FormGroup;
  selectedRec?: Reclamation;
  message = '';
  messageType = '';
  filterStatut = '';

  statuts = ['EN_ATTENTE', 'EN_COURS', 'RESOLUE', 'FERMEE', 'ANNULEE'];

  constructor(
    private reclamationService: ReclamationService,
    private clientService: ClientService,
    private agentService: AgentSavService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      description: ['', [Validators.required, Validators.minLength(10)]],
      produit: [''],
      clientId: [null, Validators.required]
    });
    this.affectForm = this.fb.group({ agentId: [null, Validators.required] });
    this.statutForm = this.fb.group({ statut: ['', Validators.required] });
    this.noteForm = this.fb.group({ note: [null, [Validators.required, Validators.min(1), Validators.max(5)]] });

    this.load();
    this.clientService.getAll().subscribe(d => this.clients = d);
    this.agentService.getAll().subscribe(d => this.agents = d);
  }

  load(): void {
    this.reclamationService.getAll().subscribe({
      next: (d) => { this.reclamations = d; this.applyFilter(); },
      error: () => this.showMessage('Erreur chargement', 'danger')
    });
  }

  applyFilter(): void {
    this.filtered = this.filterStatut
      ? this.reclamations.filter(r => r.statut === this.filterStatut)
      : [...this.reclamations];
  }

  openCreate(): void {
    this.form.reset();
    new bootstrap.Modal(document.getElementById('reclamationModal')!).show();
  }

  save(): void {
    if (this.form.invalid) return;
    const { clientId, ...data } = this.form.value;
    this.reclamationService.create(data, clientId).subscribe({
      next: () => {
        this.load();
        bootstrap.Modal.getInstance(document.getElementById('reclamationModal')!)?.hide();
        this.showMessage('Réclamation créée !', 'success');
      },
      error: (e) => this.showMessage(e.error || 'Erreur création', 'danger')
    });
  }

  openAffect(rec: Reclamation): void {
    this.selectedRec = rec;
    this.affectForm.reset();
    new bootstrap.Modal(document.getElementById('affectModal')!).show();
  }

  affecter(): void {
    if (!this.selectedRec?.id || this.affectForm.invalid) return;
    this.reclamationService.affecterAgent(this.selectedRec.id, this.affectForm.value.agentId).subscribe({
      next: () => {
        this.load();
        bootstrap.Modal.getInstance(document.getElementById('affectModal')!)?.hide();
        this.showMessage('Agent affecté !', 'success');
      },
      error: () => this.showMessage('Erreur affectation', 'danger')
    });
  }

  openStatut(rec: Reclamation): void {
    this.selectedRec = rec;
    this.statutForm.patchValue({ statut: rec.statut });
    new bootstrap.Modal(document.getElementById('statutModal')!).show();
  }

  changerStatut(): void {
    if (!this.selectedRec?.id || this.statutForm.invalid) return;
    this.reclamationService.changerStatut(this.selectedRec.id, this.statutForm.value.statut).subscribe({
      next: () => {
        this.load();
        bootstrap.Modal.getInstance(document.getElementById('statutModal')!)?.hide();
        this.showMessage('Statut modifié !', 'success');
      },
      error: () => this.showMessage('Erreur statut', 'danger')
    });
  }

  openNote(rec: Reclamation): void {
    this.selectedRec = rec;
    this.noteForm.reset();
    new bootstrap.Modal(document.getElementById('noteModal')!).show();
  }

  evaluer(): void {
    if (!this.selectedRec?.id || this.noteForm.invalid) return;
    this.reclamationService.evaluer(this.selectedRec.id, this.noteForm.value.note).subscribe({
      next: () => {
        this.load();
        bootstrap.Modal.getInstance(document.getElementById('noteModal')!)?.hide();
        this.showMessage('Réclamation évaluée !', 'success');
      },
      error: () => this.showMessage('Erreur évaluation', 'danger')
    });
  }

  delete(id: number): void {
    if (!confirm('Supprimer cette réclamation ?')) return;
    this.reclamationService.delete(id).subscribe({
      next: () => { this.load(); this.showMessage('Réclamation supprimée !', 'success'); },
      error: () => this.showMessage('Erreur suppression', 'danger')
    });
  }

  voirDetail(id: number): void {
    this.router.navigate(['/reclamations', id]);
  }

  getBadgeClass(statut?: string): string {
    return `badge badge-statut-${statut ?? 'EN_ATTENTE'}`;
  }

  getStars(note?: number): number[] {
    return note ? Array(note).fill(0) : [];
  }

  showMessage(msg: string, type: string): void {
    this.message = msg; this.messageType = type;
    setTimeout(() => this.message = '', 3000);
  }
}
