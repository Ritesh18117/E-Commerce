import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderTrackingService {

  private myOrderTrackingURL = "/api/orderTracking/myOrderTracking";
  private changeStatusURL = "/api/orderTracking/changeStatus";
  private getOrderTrackingByOrderIdURL = "/api/orderTracking/getByOrderId";
  private getAllOrderTrackingURL = "/api/orderTracking/getAll";
  private sendAlertURL = "/api/orderTracking/sendAlert";

  constructor(private http:HttpClient) { }

  getMyOrderTracking(token:string):Observable<any>{
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(this.myOrderTrackingURL, {headers});
  }

  changeStatus(token:string,orderTrackingId:number,status:string):Observable<any>{
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(`${this.changeStatusURL}/${orderTrackingId}/${status}`, {headers});
  }

  sendAlert(token:string,orderTrackingId:number):Observable<any>{
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(`${this.sendAlertURL}/${orderTrackingId}`, {headers});
  }

  getOrderTrackingByOrderId(token:string,orderId:number){
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(`${this.getOrderTrackingByOrderIdURL}/${orderId}`, {headers});
  }

  getAllOrderTracking(token:string):Observable<any>{
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(`${this.getAllOrderTrackingURL}`, {headers});
  }

  
}
