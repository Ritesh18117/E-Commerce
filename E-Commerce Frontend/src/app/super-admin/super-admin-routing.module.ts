import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
// import { SellerDashboardComponent } from './seller-dashboard/seller-dashboard.component';
import { SuperAdminDashboardComponent } from './super-admin-dashboard/super-admin-dashboard.component';

const routes: Routes = [
    { path:'dashboard', component:SuperAdminDashboardComponent}
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class SuperAdminRoutingModule { }
