import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { OrderTrackingService } from 'src/app/Services/order-tracking.service';

@Component({
  selector: 'app-order-track',
  templateUrl: './order-track.component.html',
  styleUrls: ['./order-track.component.css'],
  providers: [DatePipe]
})
export class OrderTrackComponent {

  token:any;
  myOrderTracking:any;

  constructor(private _orderTracking:OrderTrackingService,private datePipe: DatePipe){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getMyOrderTracking();
  }

  formatDate(dateString: string) {
    return this.datePipe.transform(dateString, 'yyyy-MM-dd');
  }

  getMyOrderTracking(){
    this._orderTracking.getMyOrderTracking(this.token).subscribe(
      (data) =>{
        console.log(data);
        for (let ot of data) { // Assuming `data` is an array of objects
          ot.statusChangedAt = this.formatDate(ot.statusChangedAt);
        }
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
