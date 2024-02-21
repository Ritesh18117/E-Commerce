import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartItemService {

  private addToCartURL = '/api/cart/addToCart';
  private getCartItemURL = '/api/cart/myCart';
  private removeItemURL = '/api/cart/removeItem';
  private addQuantityURL = '/api/cart/addQuantity';
  private substarctQuantityURL = '/api/cart/substarctQuantity';

  constructor(private http: HttpClient) { }

  addToCart(cardItem:any, token:any): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post<any>(this.addToCartURL, cardItem, { headers });
  }

  getMyCart(token:any): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(this.getCartItemURL, {headers});
  }
  
  removeItem(token:any,id:number): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete<any>(`${this.removeItemURL}/${id}`, {headers});
  }

  addQuantity(id: number, token: any): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.patch<any>(`${this.addQuantityURL}/${id}`, null, { headers });
  }
  
  substarctQuantity(id:number,token:any): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.patch<any>(`${this.substarctQuantityURL}/${id}`, null, { headers });
  }
  
}
