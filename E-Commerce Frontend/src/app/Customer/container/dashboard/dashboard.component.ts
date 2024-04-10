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
    this.profile = true;
    this.myAddress = false;
    this.myOrders = false;
    console.log(this.profile);
    
  }
  renderMyAddress(){
    this.myAddress = true;
    this.myOrders = false;
    this.profile = false;
  }
  renderMyOrders(){
    this.myOrders = true;
    this.myAddress = false;
    this.profile = false;
  }

}
