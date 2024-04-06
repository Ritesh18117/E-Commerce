import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { OrderTrackingService } from 'src/app/Services/order-tracking.service';

@Component({
  selector: 'app-delivery-track',
  templateUrl: './delivery-track.component.html',
  styleUrl: './delivery-track.component.css',
  providers: [DatePipe]
})
export class DeliveryTrackComponent {

  token:any;
  allOrderTracking:any;
  orderTracking:any;

  constructor(private _orderTracking:OrderTrackingService,
            private datePipe: DatePipe,
          private toastr:ToastrService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getAllOrderTracking();
  }

  getAllOrderTracking(){
    this._orderTracking.getAllOrderTracking(this.token).subscribe(
      (data) =>{
        console.log(data);
        this.allOrderTracking = data
        this.orderTracking = this.allOrderTracking;
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
        this.toastr.success("Alert Send Successfully","Success");
      }, (error) =>{
        console.error("Error",error);
        this.toastr.error("Alert Send Error","Error");
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

  all(){
    this.orderTracking = this.allOrderTracking;
  }

  delivered(){
    let deliveredOrderTracking:any = []; // Initialize the array
    for(let ot of this.allOrderTracking){
      if(ot.status == "DELIVERED"){
        deliveredOrderTracking.push(ot); // Use parentheses for push method
      }
    }
    this.orderTracking = deliveredOrderTracking;
  }
  
  inProcess(){
    let inProcessOrderTracking:any = []; // Initialize the array
    for(let ot of this.allOrderTracking){
      if(ot.status != "DELIVERED" && !this.isDateOlderThan3Days(ot.statusChangedAt)){
        inProcessOrderTracking.push(ot); // Use parentheses for push method
      }
    }
    this.orderTracking = inProcessOrderTracking;
  }
  
  toSendAlert(){
    let GotAlertOrderTracking:any = []; // Initialize the array
    for(let ot of this.allOrderTracking){
      if(ot.status != "DELIVERED" && this.isDateOlderThan3Days(ot.statusChangedAt)){
        GotAlertOrderTracking.push(ot); // Use parentheses for push method
      }
    }
    this.orderTracking = GotAlertOrderTracking;
  }
  
}
