import { Component } from '@angular/core';
import { SellerAuthService } from '../../auth/auth.service';
import { ProductServiceService } from 'src/app/Services/product-service.service';
import { ProductVariationService } from 'src/app/Services/product-variation.service';
import { ToastrService } from 'ngx-toastr';
import { CategoryService } from 'src/app/Services/category.service';

@Component({
  selector: 'app-added-items',
  templateUrl: './added-items.component.html',
  styleUrls: ['./added-items.component.css']
})
export class AddedItemsComponent {
  
  token:any;
  myProducts:any;
  size_quant:Array<[string, string]> = [];
  size:string = "";
  quantity:string = "";

  activeProductId: any = null;
  editProductTag:boolean = false;

  categories:any;

  constructor(private _categoryService:CategoryService,private toastr: ToastrService,private _productService:ProductServiceService, private _productVariationService:ProductVariationService){}
  
  async ngOnInit(){
    this.token = sessionStorage.getItem('token');
    await this.getSellerProduct();
  }

  async getSellerProduct():Promise<any>{
    try{
        const data = await this._productService.getSellersProduct(this.token).toPromise();
        this.myProducts = data;
        console.log(this.myProducts);
    } catch(error){
      console.error("Error",error);
    }
  }

  editProduct(){
    this.editProductTag = !this.editProductTag;
  }

  addQuantityShowMethod(productId: any) {
    this.size_quant = [];
    this.activeProductId = this.activeProductId === productId ? null : productId; // Toggle the active product ID
  }

  addQuantity() {
    if (this.size !== "" && this.quantity !== "") {
      let sizeQuant: [string, string] = [this.size, this.quantity];
      this.size_quant.push(sizeQuant);
      console.log(this.size_quant);
      
      this.size = "";
      this.quantity = "";
    }
  }

  deleteQuantity(index:number){
    this.size_quant.splice(index, 1);
  }

  Submit(product_id: number) {
    let productVariation: {
      product: {
        id: number;
      };
      size_quant: Array<[string, string]>; // Corrected syntax for the property
    } = {
      product: {
        id: product_id,
      },
      size_quant: this.size_quant,
    };
    this.token = sessionStorage.getItem('token');
    this._productVariationService.addProductVariation(this.token,productVariation).subscribe(
      (data) => {
        console.log(data);
        this.toastr.success('Product Added!!', 'Success');
      }, (error) =>{
        console.error("ERROR", error);
        this.toastr.error('Error submitting form', 'Error');
      }
    )
    this.size_quant = [];
    this.addQuantityShowMethod(product_id);
  }
  
  getCategory(){
    this._categoryService.getAllCategory().subscribe(
      (data) =>{
        console.log(data);
        this.categories = data;
      }, 
      (error) => {
        console.error("ERROR",error);
      }
    )
  }
}
