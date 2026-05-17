import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reclamation } from '../models/reclamation.model';

const API = '/api/reclamations';

@Injectable({ providedIn: 'root' })
export class ReclamationService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Reclamation[]> {
    return this.http.get<Reclamation[]>(API);
  }

  getById(id: number): Observable<Reclamation> {
    return this.http.get<Reclamation>(`${API}/${id}`);
  }

  create(reclamation: Reclamation, clientId: number): Observable<Reclamation> {
    return this.http.post<Reclamation>(`${API}/client/${clientId}`, reclamation);
  }

  update(id: number, reclamation: Reclamation, clientId: number): Observable<Reclamation> {
    return this.http.put<Reclamation>(`${API}/${id}/client/${clientId}`, reclamation);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${API}/${id}`, { responseType: 'text' });
  }

  affecterAgent(reclamationId: number, agentId: number): Observable<Reclamation> {
    return this.http.put<Reclamation>(`${API}/${reclamationId}/affecter/${agentId}`, {});
  }

  changerStatut(id: number, statut: string): Observable<Reclamation> {
    return this.http.put<Reclamation>(`${API}/${id}/statut`, { statut });
  }

  evaluer(id: number, note: number): Observable<Reclamation> {
    return this.http.put<Reclamation>(`${API}/${id}/evaluer`, { note });
  }

  getParClient(clientId: number): Observable<Reclamation[]> {
    return this.http.get<Reclamation[]>(`${API}/client/${clientId}`);
  }

  getParStatut(statut: string): Observable<Reclamation[]> {
    return this.http.get<Reclamation[]>(`${API}/statut/${statut}`);
  }

  getNonAffectees(): Observable<Reclamation[]> {
    return this.http.get<Reclamation[]>(`${API}/non-affectees`);
  }
}
