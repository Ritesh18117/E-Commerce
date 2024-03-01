import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { AdminRoutingModule } from './admin-routing.module';
import {AdminAuthModule } from './auth/auth.module';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { VerifySellerComponent } from './admin-dashboard/verify-seller/verify-seller.component';
import { VerifiedSellerComponent } from './admin-dashboard/verified-seller/verified-seller.component';
import { VerifiedProductComponent } from './admin-dashboard/verified-product/verified-product.component';
import { VerifyProductComponent } from './admin-dashboard/verify-product/verify-product.component';
import { AdminInfoComponent } from './admin-dashboard/admin-info/admin-info.component';
import { AddCategoryComponent } from './admin-dashboard/add-category/add-category.component';


@NgModule({
  declarations: [
    AdminDashboardComponent,
    VerifySellerComponent,
    VerifiedSellerComponent,
    VerifiedProductComponent,
    VerifyProductComponent,
    AdminInfoComponent,
    AddCategoryComponent
  ],
  imports: [
    CommonModule,
    AdminAuthModule,
    AdminRoutingModule,
    FormsModule,
    ToastrModule.forRoot()
  ]
})
export class AdminModule { 
}
