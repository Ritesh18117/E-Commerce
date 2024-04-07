import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SuperAdminRoutingModule } from './super-admin-routing.module';
import { FormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { ApproveAdminComponent } from './super-admin-dashboard/approve-admin/approve-admin.component';
import { ApprovedAdminComponent } from './super-admin-dashboard/approved-admin/approved-admin.component';
import { SuperAdminAuthModule } from './auth/auth.module';
import { ProfileComponent } from './super-admin-dashboard/profile/profile.component';
import { SuperAdminDashboardComponent } from './super-admin-dashboard/super-admin-dashboard.component';



@NgModule({
  declarations: [
    SuperAdminDashboardComponent,
    ApproveAdminComponent,
    ApprovedAdminComponent,
    ProfileComponent
  ],
  imports: [
    CommonModule,
    SuperAdminRoutingModule,
    SuperAdminAuthModule,
    FormsModule,
    ToastrModule.forRoot()
  ]
})
export class SuperAdminModule { }
