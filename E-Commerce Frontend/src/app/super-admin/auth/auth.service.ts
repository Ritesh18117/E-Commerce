import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/Services/login.service';

@Injectable({
  providedIn: 'root'
})
export class SuperAdminAuthService {
  
  public isAuthenticated = false;
  public role:any;
  constructor(private _loginService : LoginService,private _router:Router) { }

  login(username:string,password:string){
    this._loginService.login(username,password).subscribe(
      (response) => {
        if (response && response.token) {
          // Save token and role in session storage
          sessionStorage.setItem('token', response.token);
          sessionStorage.setItem('role',response.role);
          console.log(response.role);
          
          // Redirect or perform other actions as needed
          this._router.navigate(['/home']);
        }

        console.log(response.error.text);
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }

  logout(){
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("role");
    this.isAuthenticated = false;
  }

  isAuthenticate() : boolean{
    return this.isAuthenticated;
  }
}
