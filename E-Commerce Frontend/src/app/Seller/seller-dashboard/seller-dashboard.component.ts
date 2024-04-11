import { Component } from '@angular/core';
import { UserService } from 'src/app/Services/user.service';

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
  username: any;
  token:any;

  constructor(private userService:UserService){}

  ngOnInit(){
    this.getUsername();
  }
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

  getUsername(){
    this.token = sessionStorage.getItem("token");
    this.userService.getUsername(this.token).subscribe(
      (data) => {
        console.log(data);
        this.username = data.name;
      },
      (error) => {
        console.log("error",error);
      }
    )
  }
}
