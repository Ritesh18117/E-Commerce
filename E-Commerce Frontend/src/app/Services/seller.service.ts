import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SellerService {

  private getSellerProfileURL = '/api/seller/myProfile';
  private updaetProfileURL = '/api/seller/updateProfile';
  private notApprovedSellerURL = '/api/seller/notApprovedSellers';
  private approveSellerURL = "/api/seller/approveSeller";
  private rejectSellerURL = "/api/seller/rejectSeller";

  constructor(private http: HttpClient) { }

  getSellerProfile(token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(this.getSellerProfileURL, { headers });
  }

  updateProfile(token:string,profile:any):Observable<any> {
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.patch<any>(this.updaetProfileURL,profile,{headers});
  }

  notApprovedSeller(token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(this.notApprovedSellerURL, { headers });
  }

  approveSeller(token:string,sellerId:number):Observable<any>{
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(`${this.approveSellerURL}/${sellerId}`, { headers });
  }
  
  rejectSeller(token:string,sellerId:number):Observable<any>{
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(`${this.rejectSellerURL}/${sellerId}`, { headers });
  }
}
