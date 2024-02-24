import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ProductServiceService } from 'src/app/Services/product-service.service';

@Component({
  selector: 'app-verified-product',
  templateUrl: './verified-product.component.html',
  styleUrl: './verified-product.component.css'
})
export class VerifiedProductComponent {

  products:any;
  token:any;
  activeProductId:any;
  size_quant:any;
  quantity:any;
  size:any;

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
        this.toastr.success("Product Approved Successfully!!", "Success")
        this.ngOnInit();
      }, (error) => {
        console.error("Error Approving", error);
        this.toastr.error('Product Approval Error!!', 'Error');
      }
    );
  }

  rejectProduct(productId:number){
    this._productServices.rejectProduct(this.token,productId).subscribe(
      (data) =>{
        console.log(data);
        this.toastr.success("Product Rejected Successfully!!", "Success")
        this.ngOnInit();
      }, (error) => {
        console.error("Error Approving", error);
        this.toastr.error('Product Rejection Error!!', 'Error');
      }
    );
  }

}
