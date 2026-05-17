import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SuiviReclamation } from '../models/suivi-reclamation.model';

const API = 'http://localhost:9096/api/suivis';

@Injectable({ providedIn: 'root' })
export class SuiviService {
  constructor(private http: HttpClient) {}

  getByReclamation(reclamationId: number): Observable<SuiviReclamation[]> {
    return this.http.get<SuiviReclamation[]>(`${API}/reclamation/${reclamationId}`);
  }

  create(suivi: SuiviReclamation, reclamationId: number, employeId?: number): Observable<SuiviReclamation> {
    let url = `${API}/reclamation/${reclamationId}`;
    if (employeId) url += `?employeId=${employeId}`;
    return this.http.post<SuiviReclamation>(url, suivi);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${API}/${id}`, { responseType: 'text' });
  }
}
