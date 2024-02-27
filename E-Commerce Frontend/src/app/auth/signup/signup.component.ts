import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
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

  constructor(private toastr: ToastrService,private _loginService:LoginService){}

  onSubmit(){
    this._loginService.newuser(this.user).subscribe(
      (data) =>{
        console.log(data);
        this.toastr.success("Customer Account Created Successfully!","Success");
      }, (error) => {
        console.error("Error",error);
        this.toastr.error("Account Created Error!","Error");
      }
    )
    
    console.log(this.user);
    this.user.email = "";
    this.user.confirmPassword = "";
    this.user.password = "";
  }
}
