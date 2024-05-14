import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TopHeaderComponent } from './top-header/top-header.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ContainerComponent } from './Customer/container/container.component';
import { SearchComponent } from './Customer/container/search/search.component';
import { ProductsListComponent } from './Customer/container/products-list/products-list.component';
import { ProductComponent } from './Customer/container/products-list/product/product.component'
import { FormsModule } from '@angular/forms';
import { FilterComponent } from './Customer/container/products-list/filter/filter.component';
import { ProductDetailsComponent } from './Customer/container/product-details/product-details.component';
import { AboutComponent } from './about/about.component';
import { FooterComponent } from './footer/footer.component';
import { ContactComponent } from './contact/contact.component';
import { AuthModule } from './auth/auth.module'; 
import { ReactiveFormsModule } from '@angular/forms';
import { CardComponent } from './Customer/container/cart/cart.component';
import { DashboardComponent } from './Customer/container/dashboard/dashboard.component';
import { ProfileComponent } from './Customer/container/dashboard/profile/profile.component';
import { MyOrdersComponent } from './Customer/container/dashboard/my-orders/my-orders.component';
import { MyAddressComponent } from './Customer/container/dashboard/my-address/my-address.component';
import { PlaceOrderComponent } from './Customer/container/place-order/place-order.component';
import { SellerModule } from './Seller/seller.module';
import { HttpClientModule } from '@angular/common/http';
import { AuthService } from './auth/auth.service';
import { JWT_OPTIONS, JwtHelperService } from '@auth0/angular-jwt';
import { AdminModule } from './admin/admin.module';





@NgModule({
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    AuthModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    AdminModule,
    ToastrModule.forRoot()
  ],
  declarations: [
    AppComponent,
    TopHeaderComponent,
    NavbarComponent,
    ContainerComponent,
    SearchComponent,
    ProductsListComponent,
    ProductComponent,
    FilterComponent,
    ProductDetailsComponent,
    AboutComponent,
    FooterComponent,
    ContactComponent,
    CardComponent,
    DashboardComponent,
    ProfileComponent,
    MyOrdersComponent,
    MyAddressComponent,
    PlaceOrderComponent
  ],
    providers: [
    AuthService,
    JwtHelperService,
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
  ],
  bootstrap: [AppComponent] 
})
export class AppModule { }
