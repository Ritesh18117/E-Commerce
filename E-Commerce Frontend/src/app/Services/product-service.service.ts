import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductServiceService {

  private getProductApi = '/api/product/approvedProducts';
  private getSellersProductURL = '/api/product/myProducts';
  private addProductURL = '/api/product/addProduct';

  constructor(private http: HttpClient) { }

  getApprovedProducts(): Observable<any> {
    return this.http.get(`${this.getProductApi}`);
  }

  getSellersProduct(token:string):Observable<any> {
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get(this.getSellersProductURL, { headers });
  }

  addProduct(token:string, product:any):Observable<any> {
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.post<any>(this.addProductURL, product, { headers });
  }
}
