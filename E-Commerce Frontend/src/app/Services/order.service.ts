import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private myOrdersURL = "/api/order/myOrders";
  private placeOrderURL = "/api/order/placeOrder";

  constructor(private http:HttpClient) { }

  myOrder(token:any):Observable<any>{
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(this.myOrdersURL,{ headers });
  }

  placeOrder(token:any,order:any){
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    console.log(order);
    return this.http.post(this.placeOrderURL, order,{ headers });
  }

  // For Showing product Detail in ProductDetails Page
  idForShowDetails:number = -1;

  showProductDetails(productId:number){
    this.idForShowDetails = productId;
  }
}
