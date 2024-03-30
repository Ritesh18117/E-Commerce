import { Component } from '@angular/core';
import { OrderTrackingService } from 'src/app/Services/order-tracking.service';

@Component({
  selector: 'app-delivery-track',
  templateUrl: './delivery-track.component.html',
  styleUrl: './delivery-track.component.css'
})
export class DeliveryTrackComponent {

  token:any;
  

  constructor(private _orderTracking:OrderTrackingService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getAllOrderTracking();
  }

  getAllOrderTracking(){
    this._orderTracking.getAllOrderTracking(this.token).subscribe(
      (data) =>{
        console.log(data);
      },(error) =>{
        console.error(error);
      }
    )
  }

}
