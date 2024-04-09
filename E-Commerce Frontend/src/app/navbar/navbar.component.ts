import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { SellerAuthService } from '../Seller/auth/auth.service';
import { AdminAuthService } from '../admin/auth/auth.service';
import { UserService } from '../Services/user.service';
import { SuperAdminAuthService } from '../super-admin/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(private _authService:AuthService, 
              private _sellerAuthService:SellerAuthService, 
              private _adminAuthService:AdminAuthService,
              private _userService:UserService,
              private _superAdminService:SuperAdminAuthService){ }


  role:string = "" ;
  token:any;
  username:any;

  ngOnInit(){
    this.token = sessionStorage.getItem("token");
    this.getUsername();
  }

  getUsername(){
    this._userService.getUsername(this.token).subscribe(
      (data) =>{
        console.log(data);
        this.username = data.name;
      }, (error) => {
        console.error(error);
      }
    )
  }

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

  superAdmin(){
    this._superAdminService.logout();
    this.role = ""; 
  }
}
