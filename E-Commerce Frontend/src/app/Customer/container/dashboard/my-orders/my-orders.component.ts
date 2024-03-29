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
  myOrders:any;
  showDetails:boolean = false;
  showStatus:boolean = false;
  orderTracking:any;

  constructor(private _orderService:OrderService,
              private toastr:ToastrService,
              private _orderTracking:OrderTrackingService,
              private datePipe: DatePipe){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getMyOrder();
  }

  getMyOrder(){
    this._orderService.myOrder(this.token).subscribe(
      (data) =>{
        console.log(data);
        this.myOrders = data;
      },(error) =>{
        console.error("ERROR",error);
      }
    )
  }
  getOrderTrackingByOrderId(order: any){
    // Toggle status visibility only for the selected order
    order.showStatus = !order.showStatus;
    if (order.showStatus) {
        this._orderTracking.getOrderTrackingByOrderId(this.token, order.id).subscribe(
            (data) => {
                console.log(data);
                data.statusChangedAt = this.formatDate(data.statusChangedAt);
                this.orderTracking = data;
            },
            (error) => {
                console.error(error);
            }
        );
    } else {
        this.orderTracking = null; // Clear orderTracking when hiding status
    }
}

  showDetailsMethod(order: any): void {
    order.showDetails = true;
  }
  
  hideDetailsMethod(order: any): void {
    order.showDetails = false;
  }

  hideShowStatus(order: any){
    order.showStatus = !order.showStatus;
    if (!order.showStatus) {
        this.orderTracking = null;
    }
  }
  
  formatDate(dateString: string) {
    return this.datePipe.transform(dateString, 'yyyy-MM-dd');
  }

}
