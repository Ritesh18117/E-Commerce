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
    username: "",
    password: "",
    confirmPassword: "",
    role:"ROLE_CUSTOMER"
  }

  constructor(private toastr: ToastrService,private _loginService:LoginService){}

  onSubmit() {
    console.log(this.user);
    
    this._loginService.newuser(this.user).subscribe(
      (data) => {
        console.log(data);
        this.clearUserData();
        this.toastr.success("Customer Account Created Successfully!!", "success", {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      }, (error) =>{
        console.error(error);
        this.toastr.error("Customer Account Created Error!!", "error", {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      } 
    )
  }
  
  // Utility function to clear user data
  clearUserData() {
    console.log(this.user); // Log the user data if necessary before clearing
    this.user.username = "";
    this.user.confirmPassword = "";
    this.user.password = "";
  }
}
