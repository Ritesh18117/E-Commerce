import { Component } from '@angular/core';

@Component({
  selector: 'app-seller-dashboard',
  templateUrl: './seller-dashboard.component.html',
  styleUrls: ['./seller-dashboard.component.css']
})
export class SellerDashboardComponent {

  profile:boolean = true;
  addShoes:boolean = false;
  addedItems:boolean = false;
  documents: boolean = false;
  orderTrack: boolean = false;

  renderProfile(){
    this.profile = true;
    this.addShoes = false;
    this.addedItems = false;
    this.documents = false;
    this.orderTrack = false;
  }

  renderAddShoes(){
    this.addShoes = true;
    this.profile = false;
    this.addedItems = false;
    this.documents = false;
    this.orderTrack = false;
  }

  renderAddedItems(){
    this.addedItems = true;
    this.addShoes = false;
    this.profile = false;
    this.documents = false;
    this.orderTrack = false;
  }
  
  renderDocuments(){
    this.documents = true;
    this.addShoes = false;
    this.profile = false;
    this.addedItems = false;
    this.orderTrack = false;
  }

  renderOrderTrack(){
    this.orderTrack = true;
    this.documents = false;
    this.addShoes = false;
    this.profile = false;
    this.addedItems = false;
  }
}
