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
  comment:any;

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
        this.toastr.success("Seller Approved Successfully!!", "Success", {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        })
        this.ngOnInit();
      }, (error) => {
        console.error("Error Approving", error);
        this.toastr.error('Product Approval Error!!', 'Error', {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      }
    );
  }

  rejectSeller(sellerId:number){
    console.log(this.comment);
    this.sellerService.rejectSeller(this.token,sellerId,this.comment).subscribe(
      (data) =>{
        console.log(data);
        this.toastr.success("Seller Rejected Successfully!!", "Success", {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        })
        this.ngOnInit();
      }, (error) => {
        console.error("Error Approving", error);
        this.toastr.error('Product Rejection Error!!', 'Error', {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      }
    );
    this.comment = "";
  }

  convertToImage(image:any) {
    const blob = this.base64toBlob(image, 'image/png'); // Change 'image/png' to the appropriate content type
    const urlCreator = window.URL || window.webkitURL;
    return urlCreator.createObjectURL(blob);
  }

  private base64toBlob(base64Data: string, contentType: string) {
    const byteCharacters = atob(base64Data);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    return new Blob([byteArray], { type: contentType });
  }

}
