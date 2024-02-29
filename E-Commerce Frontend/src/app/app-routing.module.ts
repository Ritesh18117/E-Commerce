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

const routes: Routes = [
    { path: '', component: ContainerComponent },
    { path: 'about', component: AboutComponent },
    { path: 'contact', component: ContactComponent }, 
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
      path: 'auth',
      loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule),
      canActivate:[authGuard]
    },
    
    { path:'cart', component:CardComponent, canActivate:[authGuard] },
    { path:'dashboard', component:DashboardComponent, canActivate:[authGuard] },
    { path:'placeOrder', component:PlaceOrderComponent, canActivate:[authGuard] },
    { path: '**', redirectTo: ''} // Wild Card Route
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
