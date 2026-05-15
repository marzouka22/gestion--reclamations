import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API = 'http://localhost:800/api/rapports';

@Injectable({ providedIn: 'root' })
export class RapportService {
  constructor(private http: HttpClient) {}

  getSatisfaction(): Observable<any> {
    return this.http.get<any>(`${API}/satisfaction`);
  }

  getStatsParStatut(): Observable<any> {
    return this.http.get<any>(`${API}/stats/statut`);
  }

  getMoyenneNotes(): Observable<any> {
    return this.http.get<any>(`${API}/satisfaction/moyenne`);
  }
}
