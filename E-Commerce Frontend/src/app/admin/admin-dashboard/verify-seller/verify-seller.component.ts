import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { SellerService } from 'src/app/Services/seller.service';

@Component({
  selector: 'app-verify-seller',
  templateUrl: './verify-seller.component.html',
  styleUrl: './verify-seller.component.css'
})
export class VerifySellerComponent {

  sellers:any;
  token:any;

  constructor(private toastr: ToastrService,private sellerService:SellerService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getNotApprovedSeller();
  }

  getNotApprovedSeller(){
    this.sellerService.notApprovedSeller(this.token).subscribe(
      (data) =>{
        this.sellers = data;
        console.log(data);
      }, (error) =>{
        console.error("ERROR", error);
      }
    );
  }

  approveSeller(sellerId:number){
    this.sellerService.approveSeller(this.token,sellerId).subscribe(
      (data) =>{
        console.log(data);
        this.toastr.success("Seller Approved Successfully!!", "Success")
        this.ngOnInit();
      }, (error) => {
        console.error("Error Approving", error);
        this.toastr.error('Product Approval Error!!', 'Error');
      }
    );
  }

  rejectSeller(sellerId:number){
    this.sellerService.rejectSeller(this.token,sellerId).subscribe(
      (data) =>{
        console.log(data);
        this.toastr.success("Seller Rejected Successfully!!", "Success")
        this.ngOnInit();
      }, (error) => {
        console.error("Error Approving", error);
        this.toastr.error('Product Rejection Error!!', 'Error');
      }
    );
  }

  onSubmit(){}
}
