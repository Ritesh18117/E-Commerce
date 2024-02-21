import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
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
  
  constructor(private _authService:AuthService,private _router:Router){}

  onSubmit(){
    this._authService.login(this.user.email,this.user.password);
    this._router.navigate(['/home']);
  }

  login() {
    this._authService.login(this.user.email, this.user.password);
    this.user.email = "";
    this.user.password = "";
  }
}
