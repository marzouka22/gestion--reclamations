import { Component, OnInit } from '@angular/core';
import { RapportService } from '../../services/rapport.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  rapport: any = null;
  loading = true;

  constructor(private rapportService: RapportService) {}

  ngOnInit(): void {
    this.rapportService.getSatisfaction().subscribe({
      next: (data) => { this.rapport = data; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  getStatuts(): string[] {
    return this.rapport?.repartitionParStatut ? Object.keys(this.rapport.repartitionParStatut) : [];
  }

  getCount(statut: string): number {
    return this.rapport?.repartitionParStatut?.[statut] ?? 0;
  }

  getStars(note: number): number[] {
    return Array(note).fill(0);
  }

  getMoyenneArrondie(): string {
    const val = this.rapport?.moyenneNotes;
    return val ? parseFloat(val).toFixed(1) : 'N/A';
  }
}
