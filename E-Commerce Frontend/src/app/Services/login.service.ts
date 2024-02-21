import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private loginURL = '/api/user/generateToken'; 
  private signupURL = '/api/user/addUser'; 

  constructor(private http: HttpClient) { }

  postData(username: string, password: string): Observable<any> {
    const credentials = { username, password };
    return this.http.post<any>(`${this.loginURL}`, credentials);
  }
  
  newuser(user:any): Observable<any> {
    return this.http.post<any>(`${this.signupURL}`, user);
  }
}
