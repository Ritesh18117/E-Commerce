import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { OrderService } from 'src/app/Services/order.service';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent {

  token:any;
  myOrders:any;
  showDetails:boolean = false;

  constructor(private _orderService:OrderService,private toastr:ToastrService){}

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

  showDetailsMethod(order: any): void {
    order.showDetails = true;
  }
  
  hideDetailsMethod(order: any): void {
    order.showDetails = false;
  }
  
}
