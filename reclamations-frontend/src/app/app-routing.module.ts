import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ClientsComponent } from './components/clients/clients.component';
import { AgentsComponent } from './components/agents/agents.component';
import { ReclamationsComponent } from './components/reclamations/reclamations.component';
import { ReclamationDetailComponent } from './components/reclamation-detail/reclamation-detail.component';
import { RapportComponent } from './components/rapport/rapport.component';

const routes: Routes = [
  { path: '',               component: DashboardComponent },
  { path: 'clients',        component: ClientsComponent },
  { path: 'agents',         component: AgentsComponent },
  { path: 'reclamations',   component: ReclamationsComponent },
  { path: 'reclamations/:id', component: ReclamationDetailComponent },
  { path: 'rapport',        component: RapportComponent },
  { path: '**',             redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
