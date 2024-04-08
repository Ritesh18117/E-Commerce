import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'login', loadChildren: () => import('./login/login.module').then(m => m.loginModule) },

  // // Redirect /seller to /seller/login
  // { path: '', redirectTo: 'signup', pathMatch: 'full' },

  // // Redirect invalid paths to /seller/login
  // { path: '**', redirectTo: 'signup', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SuperAdminAuthRoutingModule { }
