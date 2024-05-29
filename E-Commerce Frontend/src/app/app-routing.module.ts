import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContainerComponent } from './Customer/container/container.component';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';
import { CardComponent } from './Customer/container/cart/cart.component';
import { authGuard } from './auth/auth.guard';
import { DashboardComponent } from './Customer/container/dashboard/dashboard.component';
import { PlaceOrderComponent } from './Customer/container/place-order/place-order.component';
import { sellerAuthGuard } from './Seller/auth/auth.guard';
import { adminAuthGuard } from './admin/auth/auth.guard';
import { ProductDetailsComponent } from './Customer/container/product-details/product-details.component';
import { superAdminAuthGuard } from './super-admin/auth/auth.guard';
import { OrderPlacedComponent } from './Customer/container/place-order/order-placed/order-placed.component';
import { MyAddressComponent } from './Customer/container/dashboard/my-address/my-address.component';
import { PricingComponent } from './Seller/pricing/pricing.component';
import { TermsConditionsComponent } from './terms-conditions/terms-conditions.component';
import { WishlistComponent } from './Customer/container/wishlist/wishlist.component';


 

//Define Routes
const routes: Routes = [
    { path: '', component: ContainerComponent },
    { path: 'about', component: AboutComponent },
    { path: 'contact', component: ContactComponent }, 
    { path: 'order-confirm', component: PlaceOrderComponent }, 
    { path: 'order-placed', component: OrderPlacedComponent },
    { path: 'customer-address', component: DashboardComponent, data: { myAddress: true }},
    { path: 'product/:id', component: ProductDetailsComponent }, // Add this line
    {
      path: 'admin',
      loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
      canActivate: [adminAuthGuard]
    },
    {
      path: 'seller',
      loadChildren: () => import('./Seller/seller.module').then(m => m.SellerModule),
      canActivate: [sellerAuthGuard]
    }, 
    {
      path: 'superAdmin',
      loadChildren: () => import('./super-admin/super-admin.module').then(m => m.SuperAdminModule),
      canActivate: [superAdminAuthGuard]
    }, 
    {
      path: 'auth',
      loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule),
      canActivate:[authGuard]
    },
    
    { path:'cart', component:CardComponent, canActivate:[authGuard] },
    { path:'dashboard', component:DashboardComponent, canActivate:[authGuard] },
    { path:'placeOrder', component:PlaceOrderComponent, canActivate:[authGuard] },
    { path: 'pricing', component: PricingComponent },
    { path: 'legal', component: TermsConditionsComponent },
        { path:'wishlist', component:WishlistComponent, canActivate:[authGuard] },

    { path: '**', redirectTo: ''} // Wild Card Route
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
