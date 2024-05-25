import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/Services/user.service';
import { ReviewService } from 'src/app/Services/review.service';

import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  // Variable For Rendering Components
  profile:boolean = true;
  myOrders:boolean = false;
  myAddress:boolean =  false;
  myReviews:boolean=false;
  username: any;
  token:any;

  constructor(private userService:UserService, private route: ActivatedRoute,private reviewService: ReviewService){}

  ngOnInit(){
    this.getUsername();
  }
  renderProfile(){
    this.profile = true;
    this.myAddress = false;
    this.myOrders = false;
    this.myReviews=false;
    console.log(this.profile);
    

    
  }
  renderMyAddress(){
    this.myAddress = true;
    this.myOrders = false;
    this.profile = false;
    this.myReviews=false;

  }
  renderMyOrders(){
    this.myOrders = true;
    this.myAddress = false;
    this.profile = false;
    this.myReviews=false;

  }
  renderMyReviews(){
    this.myOrders = false;
    this.myAddress = false;
    this.profile = false;
    this.myReviews=true;

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
