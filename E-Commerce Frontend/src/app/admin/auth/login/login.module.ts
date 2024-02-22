import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { loginRoutingModule } from './login-routing.module'
import { LoginComponent } from './login.component';

@NgModule({
  imports: [
    CommonModule,
    loginRoutingModule,
  ],
  declarations: [
    // LoginComponent
  ]
})
export class loginModule { }