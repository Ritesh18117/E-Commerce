import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
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
  allMyOrderTracking:any

  constructor(private _orderTracking:OrderTrackingService,
              private datePipe: DatePipe,
              private toastr: ToastrService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getMyOrderTracking();
  }

  getMyOrderTracking(){
    this._orderTracking.getMyOrderTracking(this.token).subscribe(
      (data) =>{
        console.log(data);
        this.allMyOrderTracking = data;
        this.myOrderTracking = this.allMyOrderTracking;
        for(let ot of this.myOrderTracking){
          ot.statusChangedAt = new Date(ot.statusChangedAt);
        }
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
        this.toastr.success('Status Changed Successfully', 'Success', {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      }, (error) =>{
        this.toastr.error('Status Change Error', 'Error', {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
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
    this.myOrderTracking = this.allMyOrderTracking;
    console.log(this.myOrderTracking);
  }

  delivered(){
    let deliveredOrderTracking:any = []; // Initialize the array
    for(let ot of this.allMyOrderTracking){
      if(ot.status == "DELIVERED"){
        deliveredOrderTracking.push(ot); // Use parentheses for push method
      }
    }
    this.myOrderTracking = deliveredOrderTracking;
    console.log(this.myOrderTracking);
  }
  
  inProcess(){
    let inProcessOrderTracking:any = []; // Initialize the array
    for(let ot of this.allMyOrderTracking){
      if(ot.status != "DELIVERED" && !this.isDateOlderThan3Days(ot.statusChangedAt)){
        inProcessOrderTracking.push(ot); // Use parentheses for push method
      }
    }
    this.myOrderTracking = inProcessOrderTracking;
    console.log(this.myOrderTracking);
  }
  
  gotWarning(){
    let toSendAlertOrderTracking:any = []; // Initialize the array
    for(let ot of this.allMyOrderTracking){
      if(ot.status != "DELIVERED" && this.isDateOlderThan3Days(ot.statusChangedAt)){
        toSendAlertOrderTracking.push(ot); // Use parentheses for push method
      }
    }
    this.myOrderTracking = toSendAlertOrderTracking;
    console.log(this.myOrderTracking);
    
  }
}
