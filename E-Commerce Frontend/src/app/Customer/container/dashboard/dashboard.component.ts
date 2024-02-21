import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  // Variable For Rendering Components
  profile:boolean = true;
  myOrders:boolean = false;
  myAddress:boolean =  false;

  renderProfile(){
    this.myAddress = false;
    this.myOrders = false;
    this.profile = true;
  }
  renderMyAddress(){
    this.myOrders = false;
    this.profile = false;
    this.myAddress = true;
  }
  renderMyOrders(){
    this.myAddress = false;
    this.profile = false;
    this.myOrders = true;
  }

}
