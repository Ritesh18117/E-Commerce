import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginService } from 'src/app/Services/login.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  user = {
    email: "",
    password: "",
    confirmPassword: "",
    role:"ROLE_CUSTOMER"
  }

  constructor(private _loginService:LoginService){}

  onSubmit(){
    this._loginService.newuser(this.user).subscribe(
      (data) =>{
        console.log(data);
      }, (error) => {
        console.error("Error",error);
      }
    )
    
    console.log(this.user);
    this.user.email = "";
    this.user.confirmPassword = "";
    this.user.password = "";
  }
}
