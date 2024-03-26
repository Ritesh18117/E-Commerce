import { Component } from '@angular/core';
import { OrderTrackingService } from 'src/app/Services/order-tracking.service';
import { SellerService } from 'src/app/Services/seller.service';

@Component({
  selector: 'app-order-track',
  templateUrl: './order-track.component.html',
  styleUrls: ['./order-track.component.css']
})
export class OrderTrackComponent {

  token:any;
  myOrderTracking:any;

  constructor(private _orderTracking:OrderTrackingService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getMyOrderTracking();
  }

  getMyOrderTracking(){
    this._orderTracking.getMyOrderTracking(this.token).subscribe(
      (data) =>{
        console.log(data);
        this.myOrderTracking = data;
      },(error)=>{
        console.error(error);
      }
    )
  }

  changeStatus(orderTrackingId:number,status:string){
    console.log(orderTrackingId,status);
    
    this._orderTracking.changeStatus(this.token,orderTrackingId,status).subscribe(
      (data) =>{
        console.log(data);
      }, (error) =>{
        console.error(error);
      }
    )
  }
}
