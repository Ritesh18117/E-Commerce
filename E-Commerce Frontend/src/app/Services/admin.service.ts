import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private myProfilrURL = 'api/admin/myProfile';
  private updaetProfileURL = 'api/admin/updateProfile';

  constructor(private _http:HttpClient) {}

  myProfile(token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this._http.get<any>(this.myProfilrURL, { headers });
  }

  updateProfile(token:string,profile:any):Observable<any> {
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this._http.patch<any>(this.updaetProfileURL,profile, { headers });
  }
}
