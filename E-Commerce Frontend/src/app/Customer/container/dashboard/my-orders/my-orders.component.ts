import { DatePipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { OrderTrackingService } from 'src/app/Services/order-tracking.service';
import { OrderService } from 'src/app/Services/order.service';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css'],
  providers: [DatePipe]
})
export class MyOrdersComponent {

  token:any;
  allMyOrder:any;
  myOrders:any;
  showDetails:boolean = false;
  showStatus:boolean = false;
  confirmation:boolean = false;
  orderTracking:any;
  selectedRadio: string = 'all';

  constructor(private _orderService:OrderService,
              private toastr:ToastrService,
              private _orderTracking:OrderTrackingService,
              private datePipe: DatePipe){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getMyOrder();
  }

  formatDate(dateString: string) {
    return this.datePipe.transform(dateString, 'yyyy-MM-dd');
  }

  getMyOrder(){
    this._orderService.myOrder(this.token).subscribe(
      (data) =>{
        console.log(data);
        this.allMyOrder = data.reverse();
        this.fetchOrderStatus();
      },(error) =>{
        console.error("ERROR",error);
      }
    )
  }
  
  fetchOrderStatus() {
    for (let order of this.allMyOrder) {
      this._orderTracking.getOrderTrackingByOrderId(this.token, order.id).subscribe(
        (data) => {
          data.statusChangedAt = this.formatDate(data.statusChangedAt);
          if(data.status == "ORDER_PLACED"){
            order.status = "Order Confirmed";
        }
          if(data.status == "PACKED"){
              order.status = "Packed";
          }
          if(data.status == "SHIPPED"){
            order.status = "Shipped";
          }
          if(data.status == "AT_DELIVERY_CENTRE"){
            order.status = "In Transit";
         }
         if(data.status == "OUT_FOR_DELIVERY"){
          order.status = "Out for Delivery";
        }
        if(data.status == "DELIVERED"){
          order.status = "Delivered";
      }
      if(data.status == "CANCELLED"){
        order.status = "Cancelled";
    }

        },
        (error) => {
          console.error(error);
        }
      );
    }
    this.myOrders = this.allMyOrder;
  }
  getOrderTrackingByOrderId(order: any){
        this._orderTracking.getOrderTrackingByOrderId(this.token, order.id).subscribe(
            (data) => {
                console.log(data);
                this.orderTracking = data;
            },
            (error) => {
                console.error(error);
            }
        );
  }

  


  showDetailsMethod(order: any): void {
    order.showDetails = true;
  }
  
  hideDetailsMethod(order: any): void {
    order.showDetails = false;
  }

  showConfirmation(order:any):void{
    order.confirmation = true;
  }

  hideConfirmation(order:any):void{
    order.confirmation = false;
  }

  hideShowStatus(order: any){
    order.showStatus = !order.showStatus;
    if (!order.showStatus) {
        this.orderTracking = null;
    }
  }
  

  cancelButton(orderTrackingId:number){
    
    console.log(orderTrackingId);
    
    this._orderTracking.cancelOrder(this.token,orderTrackingId).subscribe(
      (data) =>{
        console.log(data);
        this.toastr.success("Cancelled Successfully","Success");
      },
      (error) =>{
        console.error("error",error);
        this.toastr.error("Cancelled Error","Error");
      }
    )
  }

  all(){
    this.selectedRadio = 'all';
    this.myOrders = this.allMyOrder;
  }

  delivered(){
    this.selectedRadio = 'delivered';
    let delivered:any = [];
    for(let order of this.allMyOrder){
      this._orderTracking.getOrderTrackingByOrderId(this.token, order.id).subscribe(
        (data) => {
            data.statusChangedAt = this.formatDate(data.statusChangedAt);
            if(data.status == "DELIVERED"){
              console.log(data);
              delivered.push(data.order);
            }
        },
        (error) => {
            console.error(error);
        }
      );
    }

    this.myOrders = delivered;
  }

  inProcess(){
    this.selectedRadio = 'inProcess';
    let inProcess:any = [];
    for(let order of this.allMyOrder){
      this._orderTracking.getOrderTrackingByOrderId(this.token, order.id).subscribe(
        (data) => {
            data.statusChangedAt = this.formatDate(data.statusChangedAt);
            if(data.status != "DELIVERED" && data.status != "CANCELLED"){
              inProcess.push(data.order);
            }
        },
        (error) => {
            console.error(error);
        }
      );
    }
    this.myOrders = inProcess;
  }

  cancelled(){
    this.selectedRadio = 'cancelled';
    let cancelled:any = [];
    for(let order of this.allMyOrder){
      this._orderTracking.getOrderTrackingByOrderId(this.token, order.id).subscribe(
        (data) => {
            data.statusChangedAt = this.formatDate(data.statusChangedAt);
            if(data.status == "CANCELLED"){
              cancelled.push(data.order);
            }
        },
        (error) => {
            console.error(error);
        }
      );
    }
    this.myOrders = cancelled;
  }

  convertToImage(image:any) {
    const blob = this.base64toBlob(image, 'image/png'); // Change 'image/png' to the appropriate content type
    const urlCreator = window.URL || window.webkitURL;
    return urlCreator.createObjectURL(blob);
  }

  private base64toBlob(base64Data: string, contentType: string) {
    const byteCharacters = atob(base64Data);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    return new Blob([byteArray], { type: contentType });
  }
}
