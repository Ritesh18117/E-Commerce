import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ProductServiceService } from 'src/app/Services/product-service.service';

@Component({
  selector: 'app-verify-product',
  templateUrl: './verify-product.component.html',
  styleUrl: './verify-product.component.css'
})
export class VerifyProductComponent {
  
  products:any;
  token:any;
  activeProductId:any;

  constructor(private toastr: ToastrService,private _productServices:ProductServiceService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getNotApprovedProducts();
  }

  getNotApprovedProducts(){
    this._productServices.getNotApprovedProducts(this.token).subscribe(
      (data) =>{
        this.products = data;
        console.log(data);
      }, (error) =>{
        console.error("Error Getting products!!!");
      }
    );
  }

  approveProduct(productId:number){
    this._productServices.approveProduct(this.token,productId).subscribe(
      (data) =>{
        console.log(data);
        this.toastr.success("Product Approved Successfully!!", "Success", {
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

  rejectProduct(productId:number){
    this._productServices.rejectProduct(this.token,productId).subscribe(
      (data) =>{
        console.log(data);
        this.toastr.success("Product Rejected Successfully!!", "Success", {
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
