import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ProductServiceService } from 'src/app/Services/product-service.service';

@Component({
  selector: 'app-verified-product',
  templateUrl: './verified-product.component.html',
  styleUrl: './verified-product.component.css'
})
export class VerifiedProductComponent {

  token:any;
  productVerifiedList:any;
  searchValue:any;
  searchType:string = "companyName";

  constructor(private _productService:ProductServiceService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getMyProductVerifiedList();
  }

  getMyProductVerifiedList(){
    this._productService.getMyProductVerifyList(this.token).subscribe(
      (data) =>{
        this.productVerifiedList = data;
      }, (error) =>{
        console.error("ERROR!!",error);
      }
    )
  }

  revoke(productId:number){
    this._productService.revokeProductVerify(this.token,productId).subscribe(
      (data) =>{
        console.log(data);
        this.getMyProductVerifiedList();
      }, (error) =>{
        console.error("ERROR",error);
      }
    )
  }
}
