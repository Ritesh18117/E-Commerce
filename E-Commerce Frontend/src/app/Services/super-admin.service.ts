import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SuperAdminService {

  private getSuperAdminProfileURL = "/api/superAdmin/myProfile";
  private updateSuperAdminProfileURL = "/api/superAdmin/updateProfile";
  private getInActiveAdminURL = "/api/superAdmin/getInActiveAdmin";
  private getActiveAdminURL = "/api/superAdmin/getActiveAdmin";
  private makeAdminInActiveURL = "/api/superAdmin/makeAdminInActive";
  private makeAdminActiveURL = "/api/superAdmin/makeAdminActive";

  constructor(private http:HttpClient) { }

  getSuperAdminProfile(token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(this.getSuperAdminProfileURL, { headers });
  }

  updateSuperAdminProfile(token:string,profile:any):Observable<any> {
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.patch<any>(this.updateSuperAdminProfileURL,profile,{headers});
  }

  getInActiveAdmin(token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(this.getInActiveAdminURL, { headers });
  }

  getActiveAdmin(token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(this.getActiveAdminURL, { headers });
  }

  makeAdminInActive(token: string,adminId:number): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(`${this.makeAdminInActiveURL}/${adminId}`, { headers });
  }

  makeAdminActive(token: string,adminId:number): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(`${this.makeAdminActiveURL}/${adminId}`, { headers });
  }
}
