import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AgentSAV } from '../../models/agent-sav.model';
import { AgentSavService } from '../../services/agent-sav.service';

declare var bootstrap: any;

@Component({
  selector: 'app-agents',
  templateUrl: './agents.component.html'
})
export class AgentsComponent implements OnInit {
  agents: AgentSAV[] = [];
  form!: FormGroup;
  editMode = false;
  selectedId?: number;
  message = '';
  messageType = '';

  constructor(private agentService: AgentSavService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      nom: ['', [Validators.required, Validators.minLength(2)]],
      competence: ['', [Validators.required, Validators.minLength(2)]]
    });
    this.load();
  }

  load(): void {
    this.agentService.getAll().subscribe({
      next: (data) => this.agents = data,
      error: () => this.showMessage('Erreur de chargement', 'danger')
    });
  }

  openCreate(): void {
    this.editMode = false; this.selectedId = undefined;
    this.form.reset();
    new bootstrap.Modal(document.getElementById('agentModal')!).show();
  }

  openEdit(agent: AgentSAV): void {
    this.editMode = true; this.selectedId = agent.id;
    this.form.patchValue(agent);
    new bootstrap.Modal(document.getElementById('agentModal')!).show();
  }

  save(): void {
    if (this.form.invalid) return;
    const data: AgentSAV = this.form.value;
    const modal = bootstrap.Modal.getInstance(document.getElementById('agentModal')!);

    if (this.editMode && this.selectedId) {
      this.agentService.update(this.selectedId, data).subscribe({
        next: () => { this.load(); modal?.hide(); this.showMessage('Agent modifié !', 'success'); },
        error: () => this.showMessage('Erreur modification', 'danger')
      });
    } else {
      this.agentService.create(data).subscribe({
        next: () => { this.load(); modal?.hide(); this.showMessage('Agent créé !', 'success'); },
        error: () => this.showMessage('Erreur création', 'danger')
      });
    }
  }

  delete(id: number): void {
    if (!confirm('Supprimer cet agent ?')) return;
    this.agentService.delete(id).subscribe({
      next: () => { this.load(); this.showMessage('Agent supprimé !', 'success'); },
      error: () => this.showMessage('Erreur suppression', 'danger')
    });
  }

  showMessage(msg: string, type: string): void {
    this.message = msg; this.messageType = type;
    setTimeout(() => this.message = '', 3000);
  }
}
