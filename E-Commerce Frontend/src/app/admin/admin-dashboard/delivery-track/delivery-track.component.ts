import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { OrderTrackingService } from 'src/app/Services/order-tracking.service';

@Component({
  selector: 'app-delivery-track',
  templateUrl: './delivery-track.component.html',
  styleUrl: './delivery-track.component.css',
  providers: [DatePipe]
})
export class DeliveryTrackComponent {

  token:any;
  orderTracking:any;

  constructor(private _orderTracking:OrderTrackingService,
            private datePipe: DatePipe){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getAllOrderTracking();
  }

  getAllOrderTracking(){
    this._orderTracking.getAllOrderTracking(this.token).subscribe(
      (data) =>{
        console.log(data);
        this.orderTracking = data;
        for(let ot of this.orderTracking){
          ot.statusChangedAt = new Date(ot.statusChangedAt);
        }
      },(error) =>{
        console.error(error);
      }
    )
  }

  sendAlert(orderTrackingId:number){
    console.log("Send Alert!");
    console.log(orderTrackingId);
    this._orderTracking.sendAlert(this.token,orderTrackingId).subscribe(
      (data) =>{
        console.log(data);
      }, (error) =>{
        console.error("Error",error);
      }
    )
  }

  formatDate(dateString: string) {
    return this.datePipe.transform(dateString, 'yyyy-MM-dd');
  }

  isDateOlderThan3Days(date: Date): boolean {
    const currentDate = new Date();
    const differenceInTime = currentDate.getTime() - date.getTime();
    const differenceInDays = Math.floor(differenceInTime / (1000 * 3600 * 24));    
    return differenceInDays > 3;
  }

}
