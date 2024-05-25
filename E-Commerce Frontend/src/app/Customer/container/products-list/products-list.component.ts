import { Component, Input, Output } from '@angular/core';
import { ProductVariation } from 'src/app/Models/ProductVariation';
import { CategoryService } from 'src/app/Services/category.service';

import { ProductServiceService } from 'src/app/Services/product-service.service';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.css']
})
export class ProductsListComponent {

  products: any;
  categories: any;
  count:any = 1;

  @Input()
  searchValue!: string;

  constructor(private productService:ProductServiceService,private categoryService:CategoryService){}

  ngOnInit(){
    this.getApprovedProducts();
  }

  ngOnChanges() {
    this.onSearch();
  }


  getApprovedProducts() {
    this.productService.getApprovedProducts(this.count).subscribe(
      (data) => {
        this.products = data;
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }

  addApprovedProducts() {
    this.count++;
    this.productService.getApprovedProducts(this.count).subscribe(
      (data) => {
        this.products = this.products.concat(data);
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }
  

  onCategoryFilter(categoryId:number){
    if(categoryId === 0){
      this.getApprovedProducts();
    }else{
      console.log(categoryId);
      
      this.categoryService.getByCategoryId(categoryId).subscribe(
        (data) =>{
          this.products = data;
        },(error) =>{
          console.error("ERROR", error);
        }
      )
    }
  }

  onSearch(){
    this.productService.search(this.searchValue).subscribe(
      (data) => {
        this.products = data;
      },
      (error) => {
        console.error('Error fetching data:', error);
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

