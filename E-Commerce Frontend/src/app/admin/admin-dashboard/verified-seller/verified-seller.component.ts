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
  searchValue:any;
  searchType:string = "companyName";

  constructor(private _sellerService:SellerService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getSellerVerifiedList();
  }

  filter() {
    if(this.searchType == "all"){
      this.getSellerVerifiedList();
    }
    else if(this.searchType == "approvedSeller"){
      this._sellerService.getApprovedSellers(this.token).subscribe(
        (data) =>{
          this.sellerVerifiedList = data;
        }, (error) =>{
          console.error(error);
        }
      )
    } else if(this.searchType == "rejectedSeller"){
      this._sellerService.getRejectedSellers(this.token).subscribe(
        (data) =>{
          this.sellerVerifiedList = data;
        }, (error) =>{
          console.error(error);
        }
      )
    }
  }

  search() {
    if(this.searchType == "gstNumber"){
        this._sellerService.getSellerByGST(this.token,this.searchValue).subscribe(
          (data) =>{
            console.log(data);
            const sellers = [data];
            this.sellerVerifiedList = sellers;
          }, (error) =>{
            console.error(error);
          }
        )
    } else if(this.searchType == "companyName"){
      this._sellerService.getSellerByCompanyName(this.token,this.searchValue).subscribe(
        (data) =>{
          console.log(data);
          const sellers = [data];
          this.sellerVerifiedList = sellers;
        }, (error) =>{
          console.error(error);
        }
      )
    } else if(this.searchType == "sellerId"){
      this._sellerService.getSellerBySellerId(this.token,this.searchValue).subscribe(
        (data) =>{
          console.log(data);
          const sellers = [data];
          this.sellerVerifiedList = sellers;
        }, (error) =>{
          console.error(error);
        }
      )
    } else if(this.searchType == "licenceNumber"){
      this._sellerService.getSellerByLicenceNumber(this.token,this.searchValue).subscribe(
        (data) =>{
          console.log(data);
          const sellers = [data];
          this.sellerVerifiedList = sellers;
        }, (error) =>{
          console.error(error);
        }
      )
    }
  }

  getSellerVerifiedList(){
    this._sellerService.mySellerVerifyList(this.token).subscribe(
      (data) =>{
        console.log(data);
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
