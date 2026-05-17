import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AgentSAV } from '../models/agent-sav.model';

const API = '/api/agents';

@Injectable({ providedIn: 'root' })
export class AgentSavService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<AgentSAV[]> {
    return this.http.get<AgentSAV[]>(API);
  }

  getById(id: number): Observable<AgentSAV> {
    return this.http.get<AgentSAV>(`${API}/${id}`);
  }

  create(agent: AgentSAV): Observable<AgentSAV> {
    return this.http.post<AgentSAV>(API, agent);
  }

  update(id: number, agent: AgentSAV): Observable<AgentSAV> {
    return this.http.put<AgentSAV>(`${API}/${id}`, agent);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${API}/${id}`, { responseType: 'text' });
  }
}
