import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { CategoryService } from 'src/app/Services/category.service';
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
  searchType:string = "all";


  constructor(private _productService:ProductServiceService,
              private _categoryService:CategoryService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getMyProductVerifiedList();
  }

  getMyProductVerifiedList(){
    this._productService.getMyProductVerifyList(this.token).subscribe(
      (data) =>{
        console.log(data);
        
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

  filter(){
    if(this.searchType == "all"){
      this.getMyProductVerifiedList();
    }
    else if(this.searchType == "approvedProduct"){
      this._productService.getApprovedProducts().subscribe(
        (data) =>{
          let products: any[] = [];
          for(let product of data){
            products.push(product.product);
          }
          this.productVerifiedList = products;
        }, (error) =>{
          console.error(error);
        }
      )
    } else if(this.searchType == "rejectedProduct"){
      this._productService.getAllMyRejectedProducts(this.token).subscribe(
        (data) =>{
          this.productVerifiedList = data;
        }, (error) =>{
          console.error(error);
        }
      )
    }
  }

  search(){
    if(this.searchType == "productId"){
      this._productService.getProductById(this.searchValue).subscribe(
        (data) =>{
          let product:any[] = [];
          product.push(data[0].product)
          this.productVerifiedList = product;
        }, (error) =>{
          console.error(error);
        }
      )
    } else if(this.searchType == "category"){
      
    }
  }
}
