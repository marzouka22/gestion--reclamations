import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Reclamation } from '../../models/reclamation.model';
import { SuiviReclamation } from '../../models/suivi-reclamation.model';
import { AgentSAV } from '../../models/agent-sav.model';
import { ReclamationService } from '../../services/reclamation.service';
import { SuiviService } from '../../services/suivi.service';
import { AgentSavService } from '../../services/agent-sav.service';

declare var bootstrap: any;

@Component({
  selector: 'app-reclamation-detail',
  templateUrl: './reclamation-detail.component.html'
})
export class ReclamationDetailComponent implements OnInit {
  reclamation?: Reclamation;
  suivis: SuiviReclamation[] = [];
  agents: AgentSAV[] = [];
  suiviForm!: FormGroup;
  message = '';
  messageType = '';

  constructor(
    private route: ActivatedRoute,
    private reclamationService: ReclamationService,
    private suiviService: SuiviService,
    private agentService: AgentSavService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.suiviForm = this.fb.group({
      message: ['', [Validators.required, Validators.minLength(5)]],
      action: ['', Validators.required],
      employeId: [null]
    });
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.loadReclamation(id);
    this.loadSuivis(id);
    this.agentService.getAll().subscribe(d => this.agents = d);
  }

  loadReclamation(id: number): void {
    this.reclamationService.getById(id).subscribe(d => this.reclamation = d);
  }

  loadSuivis(id: number): void {
    this.suiviService.getByReclamation(id).subscribe(d => this.suivis = d);
  }

  openSuiviModal(): void {
    this.suiviForm.reset();
    new bootstrap.Modal(document.getElementById('suiviModal')!).show();
  }

  ajouterSuivi(): void {
    if (this.suiviForm.invalid || !this.reclamation?.id) return;
    const { employeId, ...data } = this.suiviForm.value;
    this.suiviService.create(data, this.reclamation.id, employeId || undefined).subscribe({
      next: () => {
        this.loadSuivis(this.reclamation!.id!);
        bootstrap.Modal.getInstance(document.getElementById('suiviModal')!)?.hide();
        this.showMessage('Suivi ajouté !', 'success');
      },
      error: () => this.showMessage('Erreur ajout suivi', 'danger')
    });
  }

  getBadgeClass(statut?: string): string {
    return `badge badge-statut-${statut ?? 'EN_ATTENTE'} fs-6`;
  }

  getStars(note?: number): number[] {
    return note ? Array(note).fill(0) : [];
  }

  showMessage(msg: string, type: string): void {
    this.message = msg; this.messageType = type;
    setTimeout(() => this.message = '', 3000);
  }
}
