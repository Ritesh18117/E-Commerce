import { Component } from '@angular/core';
import { SellerAuthService } from '../../auth/auth.service';
import { ProductServiceService } from 'src/app/Services/product-service.service';
import { ProductVariationService } from 'src/app/Services/product-variation.service';
import { ToastrService } from 'ngx-toastr';
import { CategoryService } from 'src/app/Services/category.service';
import { first } from 'rxjs';

@Component({
  selector: 'app-added-items',
  templateUrl: './added-items.component.html',
  styleUrls: ['./added-items.component.css']
})
export class AddedItemsComponent {

  token: any;
  myProducts: any;
  size_quant: Array<[string, string]> = [];
  size: string = "";
  quantity: string = "";

  activeProductId: any = null;
  editProductTag: boolean = false;

  categories: any;

  constructor(private _categoryService: CategoryService, 
    private toastr: ToastrService, 
    private _productService: ProductServiceService, 
    private _productVariationService: ProductVariationService) { }

  async ngOnInit() {
    this.token = sessionStorage.getItem('token');
    await this.getSellerProduct();
    this.getCategory();
  }

  async getSellerProduct(): Promise<any> {
    try {
      const data = await this._productService.getSellersProduct(this.token).toPromise();
      this.myProducts = data;
    } catch (error) {
      console.error("Error", error);
    }
  }

  editProductId: string | null = null; // ID of the product being edited
  showEditButton: boolean = true; // Flag to control visibility of the "Edit" button

  editProduct(productId: string) {
    this.editProductId = productId;
    this.showEditButton = false; // Hide the "Edit" button
  }

  cancelEdit() {
    this.editProductId = null;
    this.showEditButton = true; // Show the "Edit" button again
  }

  isEditingProduct(productId: string): boolean {
    return this.editProductId === productId;
}

  parsedValues: { first: string; second: string }[] = [];
  addQuantityShowMethod(productId: any) {
    this.parsedValues = [];
    this._productService.getProductById(productId).subscribe(
      (data) => {
        console.log(data);
        
        let size_quanttity: Array<[string, string]> = data[0].size_quan;
        // Logic for Seperating the size and quant from size_quant
        size_quanttity.forEach((tuple) => {
          var firstString: string;
          let s = "";
          if (tuple[0] != "N") {
            firstString = tuple[0];
            for (let i = 0; i < tuple.length; i++) {
              if (i > 1) {
                s += tuple[i];
              }
            }
          }
          else {
            firstString = tuple[0] + tuple[1];
            for (let i = 0; i < tuple.length; i++) {
              if (i > 3) {
                s += tuple[i];
              }
            }
          }
          this.parsedValues.push({ first: firstString, second: s.trim() })
        });
      }, (error) => {
        console.error("Error", error);
      }
    )
    this.activeProductId = this.activeProductId === productId ? null : productId; // Toggle the active product ID
  }

  addQuantity() {
    if (this.size !== "" && this.quantity !== "") {
      this.parsedValues.push({ first: this.size, second: this.quantity });
      this.size = "";
      this.quantity = "";
    }
  }

  deleteQuantity(index: number, productId: number, size: string) {
    // this.size_quant.splice(index, 1);
    console.log(size);

    this._productVariationService.deleteByProductIdAndSize(this.token, productId, size).subscribe(
      (data) => {
        console.log(data);
        this.toastr.success("Productvariation Deleted Successfully!!", "Success", {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      }, (error) => {
        console.error("ERROR", error);
        // this.toastr.error("Productvariation Deletion Error","Error");
      }
    );
    this.parsedValues.splice(index, 1);
  }

  Submit(product_id: number) {

    this.parsedValues.forEach((item) => {
      let tuple: [string, string] = [item.first, item.second];
      this.size_quant.push(tuple);
    });

    let productVariation: { // API DATA to Send
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

    this._productVariationService.addProductVariation(this.token, productVariation).subscribe(
      (data) => {
        this.toastr.success('Product Added!!', 'Success', {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      }, (error) => {
        console.error("ERROR", error);
        this.toastr.error('Error submitting form', 'Error', {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      }
    )
    this.size_quant = [];
    this.addQuantityShowMethod(product_id);
  }

  getCategory() {
    this._categoryService.getAllCategory().subscribe(
      (data) => {
        this.categories = data;
      },
      (error) => {
        console.error("ERROR", error);
      }
    )
  }

  img:string = "";

  onEditSubmit(product:any){
    console.log(product);
    product.category.categoryName = "";
    product.category.description = "";
    this._productService.updateProduct(this.token,product).subscribe(
      (data) =>{
        this.toastr.success("Product Updated Successfuly!!","success");
      }, (error) =>{
        console.error(error);
        this.toastr.error("Product Updated Error!!","error");
      }
    )
  }

  deleteImage(index:number,product:any){
    product.images.splice(index,1);
  }

  addImage(product:any){
    if(this.img !== ""){
      product.images.push(this.img);
      this.img = ""
    }
  }
}
