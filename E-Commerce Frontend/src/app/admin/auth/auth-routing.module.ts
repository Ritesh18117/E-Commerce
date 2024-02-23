import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'login', loadChildren: () => import('./login/login.module').then(m => m.loginModule) },
  { path: 'signup', loadChildren: () => import('./signup/signup.module').then(m => m.SignupModule) },
  { path: 'changepassword', loadChildren: () => import('./change-password/change-password.module').then(m => m.ChangePasswordModule) },

  // Redirect /seller to /seller/login
  { path: '', redirectTo: 'signup', pathMatch: 'full' },

  //  Do not uncomment this, this takes me 2 days to find out this is the routing problem
  // Redirect invalid paths to /seller/login
  // { path: '**', redirectTo: 'signup', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminAuthRoutingModule { }
