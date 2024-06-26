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
  productVerifiedList:any[] = [];
  searchValue:any;
  searchType:string = "approvedProduct";

  approvedProducts:any[] = [];
  rejectedProducts:any[] = [];

  approvedProductCount = 1;
  rejectedProductCount = 1;

  constructor(private _productService:ProductServiceService,
              private _categoryService:CategoryService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getMyApprovedProducts();
  }

  // getMyProductVerifiedList(){
  //   this._productService.getMyProductVerifyList(this.token).subscribe(
  //     (data) =>{
  //       console.log(data);
  //       this.productVerifiedList = data;
  //     }, (error) =>{
  //       console.error("ERROR!!",error);
  //     }
  //   )
  // }

  getMyApprovedProducts(){
    this._productService.getApprovedProducts(this.approvedProductCount).subscribe(
      (data) =>{
        console.log(data);
        for(let product of data){
          this.approvedProducts.push(product.product);
        }
        this.productVerifiedList = this.approvedProducts;
      }, (error) =>{
        console.error(error);
      }
    )
  }
  addMyApprovedProducts(){
    this.approvedProductCount++;
    this.getMyApprovedProducts();
  }

  getMyRejectedProducts(){
    this._productService.getAllMyRejectedProducts(this.token,this.rejectedProductCount).subscribe(
      (data) =>{
        this.rejectedProducts = this.rejectedProducts.concat(data);
        this.productVerifiedList = this.rejectedProducts;
      }, (error) =>{
        console.error(error);
      }
    )
  }
  addMyRejectedProducts(){
    this.rejectedProductCount++;
    this.getMyRejectedProducts();
  }

  next(){
    if(this.searchType == "approvedProduct"){
      this.addMyApprovedProducts();
    } else if(this.searchType == "rejectedProduct"){
      this.addMyRejectedProducts();
    }
  }

  filter(){
    if(this.searchType == "approvedProduct"){
      this.productVerifiedList = this.approvedProducts;
    } else if(this.searchType == "rejectedProduct"){
      if(this.rejectedProducts.length > 0){
        this.productVerifiedList = this.rejectedProducts;
      } else{
        this.getMyRejectedProducts();
      }
    }
  }

  search(){
    if(this.searchType == "productId"){
      this._productService.getProductById(this.searchValue).subscribe(
        (data) =>{   
          console.log(data);
          let product:any[] = [];
          product.push(data[0].product)
          this.productVerifiedList = product;
        }, (error) =>{
          console.error(error);
        }
      )
    } 
    // else if(this.searchType == "category"){
      
    // }
  }

  revoke(productId:number){
    this._productService.revokeProductVerify(this.token,productId).subscribe(
      (data) =>{
        console.log(data);
        // this.getMyProductVerifiedList();
        // if(this.searchType)
      }, (error) =>{
        console.error("ERROR",error);
      }
    )
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
