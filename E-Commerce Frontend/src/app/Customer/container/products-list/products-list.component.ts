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

  // For Showing in Product Details
  selectedProduct!: ProductVariation;
  showProductDetails:boolean = false;

  @Input()
  searchValue!: string;

  constructor(private productService:ProductServiceService,private categoryService:CategoryService){}

  ngOnInit(): void {
    this.getApprovedProducts();
  }

  ngOnChanges() {
    console.log("ONCHANGE");
    
    this.onSearch();
  }

  getApprovedProducts() {
    this.productService.getApprovedProducts().subscribe(
      (data) => {
        console.log(data);
        
        this.products = data;
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

}
