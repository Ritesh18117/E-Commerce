import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SellerAuthModule } from './auth/auth.module';
import { SellerDashboardComponent } from './seller-dashboard/seller-dashboard.component';
import { SellerRoutingModule } from './seller-routing.module';
import { ProfileComponent } from './seller-dashboard/profile/profile.component';
import { AddShoesComponent } from './seller-dashboard/add-shoes/add-shoes.component';
import { AddedItemsComponent } from './seller-dashboard/added-items/added-items.component';
import { FormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { DocumentsComponent } from "./seller-dashboard/documents/documents.component";
import { OrderTrackComponent } from "./seller-dashboard/order-track/order-track.component";

@NgModule({
    declarations: [
        SellerDashboardComponent,
        ProfileComponent,
        AddShoesComponent,
        AddedItemsComponent,
        OrderTrackComponent,
        DocumentsComponent
    ],
    imports: [
        CommonModule,
        SellerRoutingModule,
        SellerAuthModule,
        FormsModule,
        ToastrModule.forRoot()
    ]
})
export class SellerModule { }

