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
  private mySellerVerifyListURL = "/api/seller/getMySellerVerifyList";
  private revokeSellerVerifyURL = "/api/seller/revokeSellerVerify";

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

  mySellerVerifyList(token:string){
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(this.mySellerVerifyListURL, { headers });
  }

  revokeSellerVerify(token:string,sellerId:number){
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(`${this.revokeSellerVerifyURL}/${sellerId}`, { headers });
  }
}
