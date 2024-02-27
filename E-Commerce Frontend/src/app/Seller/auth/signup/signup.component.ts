import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
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
    role:"ROLE_SELLER"
  }

  constructor(private toastr: ToastrService,private _loginService:LoginService){}

  onSubmit() {
    console.log(this.user);
    
    this._loginService.newuser(this.user).subscribe(
      (data) => {
        console.log(data);
        this.clearUserData();
        this.toastr.success("Seller Account Created Successfully!!", "success");
      }, (error) =>{
        console.error(error);
        this.toastr.error("Seller Account Created Error!!", "error");
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
