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

  products!: any;
  categories: any;

  constructor(private productService:ProductServiceService){}

  ngOnInit(): void {
    this.getApprovedProducts();
  }

  getApprovedProducts() {
    this.productService.getApprovedProducts().subscribe(
      (data) => {
        this.products = data;
        console.log(this.products);
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }

  // For Showing in Product Details
  selectedProduct!: ProductVariation;
  showProductDetails:boolean = false;

 @Input() 
  searched:string ='';

}
