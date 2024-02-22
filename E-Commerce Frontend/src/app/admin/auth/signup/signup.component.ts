import { Component } from '@angular/core';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  user = {
    email: "",
    password: "",
    confirmPassword: ""
  }

  onSubmit(){
    console.log(this.user);
  }
}
