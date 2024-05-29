import { Injectable } from '@angular/core';
import { LoginService } from '../Services/login.service';
import { Route, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  public isAuthenticated = false;
  public role:any;
  private jwtHelper = new JwtHelperService();

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
    if(sessionStorage.getItem('token') !== null)
      return true;
    else
      return false;
  }

  getToken(){
    const token = sessionStorage.getItem('token');
    return token;
  }
  getUserIdFromToken(): string | null {
    const token = this.getToken();
    if (token) {
      const decodedToken: any = this.jwtHelper.decodeToken(token);
      return decodedToken.sub; // Directly accessing the 'sub' field
    }
    return null;
  }

  getUserRoleFromToken(): string | null {
    const token = this.getToken();
    if (token) {
      const decodedToken: any = this.jwtHelper.decodeToken(token);
      return decodedToken.role; // Adjust this according to your token's payload structure
    }
    return null;
  }
}
