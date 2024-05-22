import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { OrderTrackingService } from 'src/app/Services/order-tracking.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-order-track',
  templateUrl: './order-track.component.html',
  styleUrls: ['./order-track.component.css'],
  providers: [DatePipe]
})
export class OrderTrackComponent {

  token: any;
  myOrderTracking: any;

  activeState: string = "orderPlaced";

  orderPlacedOrderTracking: any[] = [];
  packedOrderTracking: any[] = [];
  shippedOrderTracking: any[] = [];
  atDeilveryCentreOrderTracking: any[] = [];
  outForDeliveryOrderTracking: any[] = [];
  deliveredOrderTracking: any[] = [];
  cancelledOrderTracking: any[] = [];

  orderPlacedCount = 1;
  packedCount = 1;
  shippedCount = 1;
  atDeilveryCentreCount = 1;
  outForDeliveryCount = 1;
  deliveredCount = 1;
  cancelledCount = 1;
  showAddress: boolean = false;
  addressId: any = null;
  showStatusButton: boolean = true;

  constructor(private _orderTracking: OrderTrackingService,
    private datePipe: DatePipe,
    private toastr: ToastrService,
    private location: Location ) { }

  ngOnInit() {
    this.token = sessionStorage.getItem('token');
    this.orderPlaced();
  }

  orderPlaced() {
    this.activeState = "orderPlaced";
    this._orderTracking.getMyOrderTrackingByStatus(this.token, "ORDER_PLACED", this.orderPlacedCount).subscribe(
      (data) => {
        console.log(data);
        this.orderPlacedOrderTracking = this.orderPlacedOrderTracking.concat(data);
        this.myOrderTracking = this.orderPlacedOrderTracking;
        for (let ot of this.myOrderTracking) {
          ot.statusChangedAt = new Date(ot.statusChangedAt);
          ot.showFullAddress = false; // Initialize the boolean property
        }
        this.orderPlacedCount++;
      }, (error) => {
        console.error(error);
      }
    )
  }

  packed() {
    this.activeState = "packed";
    this._orderTracking.getMyOrderTrackingByStatus(this.token, "PACKED", this.packedCount).subscribe(
      (data) => {
        this.packedOrderTracking = this.packedOrderTracking.concat(data);
        this.myOrderTracking = this.packedOrderTracking;
        for (let ot of this.myOrderTracking) {
          ot.statusChangedAt = new Date(ot.statusChangedAt);
          ot.showFullAddress = false; // Initialize the boolean property
        }
        this.packedCount++;
      }, (error) => {
        console.error(error);
      }
    )
  }

  shipped() {
    this.activeState = "shipped";
    this._orderTracking.getMyOrderTrackingByStatus(this.token, "SHIPPED", this.shippedCount).subscribe(
      (data) => {
        this.shippedOrderTracking = this.shippedOrderTracking.concat(data);
        this.myOrderTracking = this.shippedOrderTracking;
        for (let ot of this.myOrderTracking) {
          ot.statusChangedAt = new Date(ot.statusChangedAt);
          ot.showFullAddress = false; // Initialize the boolean property
        }
        this.shippedCount++;
      }, (error) => {
        console.error(error);
      }
    )
  }

  atDeliveryCentre() {
    this.activeState = "atDeliveryCentre";
    this._orderTracking.getMyOrderTrackingByStatus(this.token, "AT_DELIVERY_CENTRE", this.atDeilveryCentreCount).subscribe(
      (data) => {
        this.atDeilveryCentreOrderTracking = this.atDeilveryCentreOrderTracking.concat(data);
        this.myOrderTracking = this.atDeilveryCentreOrderTracking;
        for (let ot of this.myOrderTracking) {
          ot.statusChangedAt = new Date(ot.statusChangedAt);
          ot.showFullAddress = false; // Initialize the boolean property
        }
        this.atDeilveryCentreCount++;
      }, (error) => {
        console.error(error);
      }
    )
  }

  outForDelivery() {
    this.activeState = "outForDelivery";
    this._orderTracking.getMyOrderTrackingByStatus(this.token, "OUT_FOR_DELIVERY", this.outForDeliveryCount).subscribe(
      (data) => {
        this.outForDeliveryOrderTracking = this.outForDeliveryOrderTracking.concat(data);
        this.myOrderTracking = this.outForDeliveryOrderTracking;
        for (let ot of this.myOrderTracking) {
          ot.statusChangedAt = new Date(ot.statusChangedAt);
          ot.showFullAddress = false; // Initialize the boolean property
        }
        this.outForDeliveryCount++;
      }, (error) => {
        console.error(error);
      }
    )
  }

  delivered() {
    this.activeState = "delivered";
    this._orderTracking.getMyOrderTrackingByStatus(this.token, "DELIVERED", this.deliveredCount).subscribe(
      (data) => {
        this.deliveredOrderTracking = this.deliveredOrderTracking.concat(data);
        this.myOrderTracking = this.deliveredOrderTracking;
        for (let ot of this.myOrderTracking) {
          ot.statusChangedAt = new Date(ot.statusChangedAt);
          ot.showFullAddress = false; // Initialize the boolean property
        }
        this.deliveredCount++;
      }, (error) => {
        console.error(error);
      }
    )
  }

  cancelled() {
    this.activeState = "cancelled";
    this._orderTracking.getMyOrderTrackingByStatus(this.token, "CANCELLED", this.cancelledCount).subscribe(
      (data) => {
        this.cancelledOrderTracking = this.cancelledOrderTracking.concat(data);
        this.myOrderTracking = this.cancelledOrderTracking;
        for (let ot of this.myOrderTracking) {
          ot.statusChangedAt = new Date(ot.statusChangedAt);
          ot.showFullAddress = false; // Initialize the boolean property
        }
        this.cancelledCount++;
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

  changeStatus(orderTrackingId: number, status: string, index: number, myOrderTracing: any): void {
    console.log(orderTrackingId, status);
    this._orderTracking.changeStatus(this.token, orderTrackingId, status).subscribe(
      (data) => {
        console.log(data);
        this.toastr.success('Status Changed Successfully', 'Success', {
          timeOut: 500,
        });
        this.myOrderTracking[index].status = status;
        window.location.reload();
      },
      (error) => {
        this.toastr.error('Status Change Error', 'Error', {
          timeOut: 500,
        });
      }
    );
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

  toggleFullAddress(index: number) {
    this.myOrderTracking[index].showFullAddress = !this.myOrderTracking[index].showFullAddress;
}
  toggleAddress(id: number){
    this.addressId = id;
    this.showAddress = !this.showAddress;
  }
  showingFullAddress(id: number){
    return this.addressId === id;
  }
  showLessAddress(){
    this.addressId = null;
    this.showAddress = !this.showAddress;
  }
  
  sliceAddress(address: string, start:number, end:number){
    const sliced_address = address.slice(start, end);
    return sliced_address;
  }
  sliceAddressSecond(address: string, start:number){
    const sliced_address = address.slice(start, address.length);
    return sliced_address;
  }

  showStatus(state: string): string {
    if (state === "ORDER_PLACED") {
        return "Order Placed";
    } else if (state === "PACKED") {
        return "Packed";
    } else if (state === "SHIPPED") {
        return "Shipped";
    } else if (state === "AT_DELIVERY_CENTRE") {
        return "In Transit";
    } else if (state === "DELIVERED") {
        return "Delivered";
    } else if (state === "CANCELLED") {
        return "Cancelled";
    } else {
        return "Unknown Status"; // Add a default return value for unmatched states
    }
}
}