import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client } from '../models/client.model';

const API = 'http://localhost:9096/api/clients';

@Injectable({ providedIn: 'root' })
export class ClientService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Client[]> {
    return this.http.get<Client[]>(API);
  }

  getById(id: number): Observable<Client> {
    return this.http.get<Client>(`${API}/${id}`);
  }

  create(client: Client): Observable<Client> {
    return this.http.post<Client>(API, client);
  }

  update(id: number, client: Client): Observable<Client> {
    return this.http.put<Client>(`${API}/${id}`, client);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${API}/${id}`, { responseType: 'text' });
  }

  rechercher(nom: string): Observable<Client[]> {
    return this.http.get<Client[]>(`${API}/recherche?nom=${nom}`);
  }
}
