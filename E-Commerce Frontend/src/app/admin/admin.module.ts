import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { AdminRoutingModule } from './admin-routing.module';
import {AdminAuthModule } from './auth/auth.module';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AdminAuthModule,
    AdminRoutingModule,
    FormsModule,
    ToastrModule.forRoot()
  ]
})
export class AdminModule { }
