import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerAddressService {

  private getMyAddressesURL = '/api/address/myAddresses';
  private postAddressURL = '/api/address/addAddress';
  private deleteAddressURL = '/api/address/deleteAddress';

  constructor(private http: HttpClient) { }

  getAddress(token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(this.getMyAddressesURL, { headers });
  }

  postAddress(address:any, token:string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post<any>(this.postAddressURL, address, { headers });
  }

  deleteAdddress(id:number,token:string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete<any>(`${this.deleteAddressURL}/${id}`, { headers });
  }
}
