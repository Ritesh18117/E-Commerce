import { Component } from '@angular/core';
import { SuperAdminAuthService } from '../auth.service';
import { Router } from '@angular/router';
import { SuperAdminAuthModule } from '../auth.module';

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
  
  constructor(private _superAdminAuthService:SuperAdminAuthService,private _router:Router){}

  onSubmit(){
    this._superAdminAuthService.login(this.user.email,this.user.password);
    this._router.navigate(['/home']);
  }
}
