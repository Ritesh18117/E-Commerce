import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private getUsernameURL = "/api/user/getUsername";

  constructor(private _http:HttpClient) { }

  getUsername(token: string):Observable<any>{
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this._http.get<any>(this.getUsernameURL, { headers });
  }
}
