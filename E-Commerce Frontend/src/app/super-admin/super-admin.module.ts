import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SuperAdminRoutingModule } from './super-admin-routing.module';
import { FormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { ApproveAdminComponent } from './super-admin-dashboard/approve-admin/approve-admin.component';



@NgModule({
  declarations: [
    ApproveAdminComponent,
    ApproveAdminComponent
  ],
  imports: [
    CommonModule,
    SuperAdminRoutingModule,
    FormsModule,
    ToastrModule.forRoot()
  ]
})
export class SuperAdminModule { }
