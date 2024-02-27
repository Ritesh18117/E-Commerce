import { Component } from '@angular/core';
import { SellerService } from 'src/app/Services/seller.service';

@Component({
  selector: 'app-verified-seller',
  templateUrl: './verified-seller.component.html',
  styleUrl: './verified-seller.component.css'
})
export class VerifiedSellerComponent {

  token:any;
  sellerVerifiedList:any;

  constructor(private _sellerService:SellerService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getSellerVerifiedList();
  }

  getSellerVerifiedList(){
    this._sellerService.mySellerVerifyList(this.token).subscribe(
      (data) =>{
        this.sellerVerifiedList = data;
      }, (error) =>{
        console.error("ERROR!!",error);
      }
    )
  }

  revoke(sellerId:number){
    this._sellerService.revokeSellerVerify(this.token,sellerId).subscribe(
      (data) =>{
        console.log(data);
        this.getSellerVerifiedList();
      }, (error) =>{
        console.error("ERROR",error);
      }
    )
  }
}
