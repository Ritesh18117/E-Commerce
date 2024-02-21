import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class CustomerProfileService {

  private getCustomerURL = '/api/customer/myProfile'; 
  private updateCustomerURL = '/api/customer/updateCustomer';

  constructor(private http: HttpClient) { }

  getCustomer(token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(this.getCustomerURL, { headers });
  }

  updateCustomer(token: string, profile:any): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.patch<any>(this.updateCustomerURL,profile, { headers });
  }
}
