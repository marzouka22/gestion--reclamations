import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Client } from '../../models/client.model';
import { ClientService } from '../../services/client.service';

declare var bootstrap: any;

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html'
})
export class ClientsComponent implements OnInit {
  clients: Client[] = [];
  filtered: Client[] = [];
  form!: FormGroup;
  editMode = false;
  selectedId?: number;
  message = '';
  messageType = '';
  searchTerm = '';

  constructor(private clientService: ClientService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initForm();
    this.load();
  }

  initForm(): void {
    this.form = this.fb.group({
      nom: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['', [Validators.required, Validators.pattern(/^[0-9]{8}$/)]]
    });
  }

  load(): void {
    this.clientService.getAll().subscribe({
      next: (data) => { this.clients = data; this.filtered = data; },
      error: () => this.showMessage('Erreur de chargement', 'danger')
    });
  }

  filter(): void {
    const t = this.searchTerm.toLowerCase();
    this.filtered = this.clients.filter(c =>
      c.nom.toLowerCase().includes(t) ||
      c.email.toLowerCase().includes(t) ||
      c.telephone.includes(t)
    );
  }

  openCreate(): void {
    this.editMode = false;
    this.selectedId = undefined;
    this.form.reset();
    this.showModal('clientModal');
  }

  openEdit(client: Client): void {
    this.editMode = true;
    this.selectedId = client.id;
    this.form.patchValue(client);
    this.showModal('clientModal');
  }

  save(): void {
    if (this.form.invalid) return;
    const data: Client = this.form.value;

    if (this.editMode && this.selectedId) {
      this.clientService.update(this.selectedId, data).subscribe({
        next: () => { this.load(); this.hideModal('clientModal'); this.showMessage('Client modifié !', 'success'); },
        error: (e) => this.showMessage(e.error || 'Erreur modification', 'danger')
      });
    } else {
      this.clientService.create(data).subscribe({
        next: () => { this.load(); this.hideModal('clientModal'); this.showMessage('Client créé !', 'success'); },
        error: (e) => this.showMessage(e.error || 'Erreur création', 'danger')
      });
    }
  }

  delete(id: number): void {
    if (!confirm('Supprimer ce client ?')) return;
    this.clientService.delete(id).subscribe({
      next: () => { this.load(); this.showMessage('Client supprimé !', 'success'); },
      error: () => this.showMessage('Erreur suppression', 'danger')
    });
  }

  showMessage(msg: string, type: string): void {
    this.message = msg; this.messageType = type;
    setTimeout(() => this.message = '', 3000);
  }

  showModal(id: string): void {
    new bootstrap.Modal(document.getElementById(id)!).show();
  }

  hideModal(id: string): void {
    bootstrap.Modal.getInstance(document.getElementById(id)!)?.hide();
  }
}
