import { Component, Input, Output } from '@angular/core';
import { Product } from 'src/app/Models/Product';
import { ProductVariation } from 'src/app/Models/ProductVariation';
import { ProductServiceService } from 'src/app/Services/product-service.service';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.css']
})
export class ProductsListComponent {

  products!: any;

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
  
  // For Sending Data to Filter Component 
  // total = this.products.length;
  // inStock = this.products.filter(x => x.is_in_inventory === true).length;
  // notInStock = this.products.filter(x => x.is_in_inventory === false).length;

  selectedRadioButton: string = 'all';

  // For Radio Buttons
  onFilterChanged(event: string){
    console.log(event);
    this.selectedRadioButton = event;
  }

}
