import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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
  private getSellerByGSTURL = "/api/seller/getSellerByGST";
  private getSellerByLicenceNumberURL = "/api/seller/getSellerByLicenceNumber";
  private getSellerByCompanyNameURL = "/api/seller/getSellerByCompanyName";
  private getSellerBySellerIdURL = "/api/seller/getSellerBySellerId";
  private getApprovedSellerURL = "/api/seller/approvedSellers";
  private getRejectedSellerURL = "/api/seller/rejectedSellers";
  private getMyOrderTrakingURL = "/api/seller/myOrderTracking";
  private uploadDocumentsURL = '/api/seller/uploadDocument';
  private getMyDocumentsURL = "/api/seller/getMyDocuments";

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
  
  rejectSeller(token: string, sellerId: number, comment: string): Observable<any> {
    const params = new HttpParams().set('comment', comment);
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(`${this.rejectSellerURL}/${sellerId}`, { headers, params });
  }

  mySellerVerifyList(token:string):Observable<any>{
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(this.mySellerVerifyListURL, { headers });
  }

  revokeSellerVerify(token:string,sellerId:number):Observable<any>{
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(`${this.revokeSellerVerifyURL}/${sellerId}`, { headers });
  }

  getSellerByGST(token:string,gst:string):Observable<any> {
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(`${this.getSellerByGSTURL}/${gst}`, { headers });
  }

  getSellerByLicenceNumber(token:string,licenceNumber:string):Observable<any> {
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(`${this.getSellerByLicenceNumberURL}/${licenceNumber}`, { headers });
  }

  getSellerByCompanyName(token:string,companyName:string):Observable<any> {
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(`${this.getSellerByCompanyNameURL}/${companyName}`, { headers });
  }

  getSellerBySellerId(token:string,sellerId:string):Observable<any> {
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(`${this.getSellerBySellerIdURL}/${sellerId}`, { headers });
  }

  getApprovedSellers(token:string):Observable<any>{
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(this.getApprovedSellerURL, {headers});
  }
  
  getRejectedSellers(token:string):Observable<any>{
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(this.getRejectedSellerURL, {headers});
  }

  getMyOrderTraking(token:string):Observable<any>{
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(this.getMyOrderTrakingURL, {headers});
  }

  uploadDocuments(token: string, formData:any) {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post<any>(this.uploadDocumentsURL, formData, { headers });
  }

  getMyDocuments(token:string):Observable<any>{
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(this.getMyDocumentsURL, { headers });
  }
}