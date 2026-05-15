import { Component, OnInit } from '@angular/core';
import { RapportService } from '../../services/rapport.service';

@Component({
  selector: 'app-rapport',
  templateUrl: './rapport.component.html'
})
export class RapportComponent implements OnInit {
  rapport: any = null;
  loading = true;

  constructor(private rapportService: RapportService) {}

  ngOnInit(): void {
    this.rapportService.getSatisfaction().subscribe({
      next: (d) => { this.rapport = d; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  keys(obj: any): string[] {
    return obj ? Object.keys(obj) : [];
  }

  getMoyenneArrondie(): string {
    const v = this.rapport?.moyenneNotes;
    return v ? parseFloat(v).toFixed(2) : 'N/A';
  }

  getNote(key: string): string {
    return key.replace('note_', '').replace('_etoile(s)', '') + ' ★';
  }

  getBadgeColor(statut: string): string {
    const map: any = {
      'EN_ATTENTE': 'warning', 'EN_COURS': 'primary',
      'RESOLUE': 'success', 'FERMEE': 'secondary', 'ANNULEE': 'danger'
    };
    return map[statut] || 'secondary';
  }
}
