import { Component } from '@angular/core';
import { SellerAuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  user = {
    email:"",
    password:""
  }
  
  constructor(private _sellerAuthService:SellerAuthService,private _router:Router){}

  onSubmit(){
    this._sellerAuthService.login(this.user.email,this.user.password);
    this._router.navigate(['/home']);
  }
}
