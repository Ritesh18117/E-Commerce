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

  activeState:string = "orderPlaced";

  orderPlacedOrderTracking:any[] = [];
  packedOrderTracking:any[] = [];
  shippedOrderTracking:any[] = [];
  atDeilveryCentreOrderTracking:any[] = [];
  outForDeliveryOrderTracking:any[] = [];
  deliveredOrderTracking:any[] = [];
  cancelledOrderTracking:any[] = [];

  orderPlacedCount = 1;
  packedCount = 1;
  shippedCount = 1;
  atDeilveryCentreCount = 1;
  outForDeliveryCount = 1;
  deliveredCount = 1;
  cancelledCount = 1;

  constructor(private _orderTracking:OrderTrackingService,
              private datePipe: DatePipe,
              private toastr:ToastrService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.orderPlaced();
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

  orderPlaced(){
    this.activeState = "orderPlaced";
    this._orderTracking.getOrderTrackingByOrderStatus(this.token,"ORDER_PLACED",this.orderPlacedCount).subscribe(
      (data) => {
        console.log(data);
        this.orderPlacedOrderTracking = this.orderPlacedOrderTracking.concat(data);
        this.orderTracking = this.orderPlacedOrderTracking;
        for (let ot of this.orderTracking) {
          ot.statusChangedAt = new Date(ot.statusChangedAt);
        }
        // console.log(this.myOrderTracking);
        this.orderPlacedCount++;
      }, (error) => {
        console.error(error);
      }
    )
  }

  packed(){
    this.activeState = "packed";
    this._orderTracking.getOrderTrackingByOrderStatus(this.token,"PACKED",this.packedCount).subscribe(
      (data) => {
        // console.log(data);
        this.packedOrderTracking = this.packedOrderTracking.concat(data);
        this.orderTracking = this.packedOrderTracking;
        for (let ot of this.orderTracking) {
          ot.statusChangedAt = new Date(ot.statusChangedAt);
        }
        this.packedCount++;
        // console.log(this.myOrderTracking);
      }, (error) => {
        console.error(error);
      }
    )
  }

  shipped(){
    this.activeState = "shipped";
    this._orderTracking.getOrderTrackingByOrderStatus(this.token,"SHIPPED",this.shippedCount).subscribe(
      (data) => {
        // console.log(data);
        this.shippedOrderTracking = this.shippedOrderTracking.concat(data);
        this.orderTracking = this.shippedOrderTracking;
        for (let ot of this.orderTracking) {
          ot.statusChangedAt = new Date(ot.statusChangedAt);
        }
        this.shippedCount++;
        // console.log(this.myOrderTracking);
      }, (error) => {
        console.error(error);
      }
    )
  }

  atDeliveryCentre(){
    this.activeState = "atDeliveryCentre";
    this._orderTracking.getOrderTrackingByOrderStatus(this.token,"AT_DELIVERY_CENTRE",this.atDeilveryCentreCount).subscribe(
      (data) => {
        // console.log(data);
        this.atDeilveryCentreOrderTracking = this.atDeilveryCentreOrderTracking.concat(data);
        this.orderTracking = this.atDeilveryCentreOrderTracking;
        for (let ot of this.orderTracking) {
          ot.statusChangedAt = new Date(ot.statusChangedAt);
        }
        this.atDeilveryCentreCount++;
        // console.log(this.myOrderTracking);
      }, (error) => {
        console.error(error);
      }
    )
  }

  outForDelivery(){
    this.activeState = "outForDelivery";
    this._orderTracking.getOrderTrackingByOrderStatus(this.token,"OUT_FOR_DELIVERY",this.outForDeliveryCount).subscribe(
      (data) => {
        // console.log(data);
        this.outForDeliveryOrderTracking = this.outForDeliveryOrderTracking.concat(data);
        this.orderTracking = this.outForDeliveryOrderTracking;
        for (let ot of this.orderTracking) {
          ot.statusChangedAt = new Date(ot.statusChangedAt);
        }
        this.outForDeliveryCount++;
        // console.log(this.myOrderTracking);
      }, (error) => {
        console.error(error);
      }
    )
  }

  delivered(){
    this.activeState = "delivered";
    this._orderTracking.getOrderTrackingByOrderStatus(this.token,"DELIVERED",this.deliveredCount).subscribe(
      (data) => {
        // console.log(data);
        this.deliveredOrderTracking = this.deliveredOrderTracking.concat(data);
        this.orderTracking = this.deliveredOrderTracking;
        for (let ot of this.orderTracking) {
          ot.statusChangedAt = new Date(ot.statusChangedAt);
        }
        this.deliveredCount++;
        // console.log(this.myOrderTracking);
      }, (error) => {
        console.error(error);
      }
    )
  }

  cancelled(){
    this.activeState = "cancelled";
    this._orderTracking.getOrderTrackingByOrderStatus(this.token,"CANCELLED",this.cancelledCount).subscribe(
      (data) => {
        // console.log(data);
        this.cancelledOrderTracking = this.cancelledOrderTracking.concat(data);
        this.orderTracking = this.cancelledOrderTracking;
        for (let ot of this.orderTracking) {
          ot.statusChangedAt = new Date(ot.statusChangedAt);
        }
        this.cancelledCount++;
        // console.log(this.myOrderTracking);
      }, (error) => {
        console.error(error);
      }
    )
  }

  next(){
    if(this.activeState == "orderPlaced"){
      this.orderPlaced();
    }
    else if(this.activeState == "packed"){
      this.packed();
    }
    else if(this.activeState == "shipped"){
      this.shipped();
    }
    else if(this.activeState == "atDeliveryCentre"){
      this.atDeliveryCentre();
    }
    else if(this.activeState == "outForDelivery"){
      this.outForDelivery();
    }
    else if(this.activeState == "delivered"){
      this.delivered();
    } else{
      this.cancelled();
    }
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
}
