import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { SellerAuthService } from '../Seller/auth/auth.service';
import { AdminAuthService } from '../admin/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(private _authService:AuthService, private _sellerAuthService:SellerAuthService, private _adminAuthService:AdminAuthService){ }


  role:string = "" ;

  ngAfterContentChecked(){
    if(sessionStorage.getItem('role') !== null){
      const storedRole = sessionStorage.getItem('role');
      if(storedRole !== null){
        this.role = storedRole;
      }else{
        console.log("Error from nav-comp!!!");
        this.role = "";
      }
    }
  }

  logout(){
    this._authService.logout();
    this.role = "";
  }

  Sellerlogout(){
    this._sellerAuthService.logout();
    this.role = "";
  }

  Adminlogout(){
    this._adminAuthService.logout();
    this.role = "";
  }
}
