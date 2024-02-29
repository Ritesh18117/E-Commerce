import { Component, EventEmitter, Input, Output } from '@angular/core';
import { cardItem } from 'src/app/Models/cardItem';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/auth/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CartItemService } from 'src/app/Services/cart-item.service';
import { ProductServiceService } from 'src/app/Services/product-service.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css'],
})
export class ProductDetailsComponent {
  constructor(
    private toastr: ToastrService,
    private _authService: AuthService,
    private _cartItemService: CartItemService,
    private route: ActivatedRoute,
    private _productService:ProductServiceService
  ) {}

  // For Loading product when component is loaded
  product!: any;
  role:any = "null";
  cardItem!: cardItem;
  productId: string | null = null;
  // Variables for card Items
  selectedSize: string = "";
  inStock:number = -1;
  selectedImage:any;

  // Load product at first to show to details page
  ngOnInit() {
    this.role = sessionStorage.getItem('role');
    this.productId = this.route.snapshot.paramMap.get('id');
    this.getProductById();
  }

  parsedValues: { size: string; quantity: string }[] = [];
  getProductById(){
    if (this.productId !== null) {
      this._productService.getProductById(parseInt(this.productId)).subscribe(
        (data) => {
          this.product = data;
          this.selectedImage = data[0].product.imageURL;
          let size_quanttity: Array<[string, string]> = data[0].size_quan;
          // Logic for Seperating the size and quant from size_quant
          size_quanttity.forEach((tuple) => {
            let first = "";
            let s = "";
            let comaOccurs = false;
            // size = tuple[0] + tuple[1];
            for (let i = 0; i < tuple.length; i++) {
              if(comaOccurs == false && tuple[i] != ","){
                first += tuple[0];
                comaOccurs = true;
              }
              if(comaOccurs == true && tuple[i] !=" "){
                s += tuple[i];
              }
            }
            this.parsedValues.push({ size: first, quantity: s.trim() })
          });

        }, (error) => {
          console.error("Error", error);
          this.toastr.error("Product Finding Error!!","error");
        }
      );
    }
  }

  // Size Selection
  pickSize(event: any) {
    const selectedSize = event.target.value;
    const selectedSizeObject = this.parsedValues.find(x => x.size === selectedSize);

    if (selectedSizeObject) {
      this.selectedSize = selectedSizeObject.size;
      this.inStock = parseInt(selectedSizeObject.quantity);
    }
  }
  

  // Add to card
  addToCard(product: any) {
    const token = this._authService.getToken();
    if (this._authService.isAuthenticate()) {
      if (!this.cardItem) {
        this.cardItem = product;
      }
      if (this.selectedSize != "") {
        const addToCartItem = {
          product_id: `${product.product.id}`,
          size: `${this.selectedSize}`,
          quantity: 1,
        };
        console.log(addToCartItem);
        
        this._cartItemService.addToCart(addToCartItem, token).subscribe(
          (data) => {
            console.log('Successfull!!');
          },
          (error) => {
            console.error('Error Found');
          }
        );
        this.toastr.success('Product Added!!', 'Success');
      } else {
        this.toastr.warning('Please Select Size!', 'warning');
      }
    } else {
      this.toastr.warning('Please login First!!');
    }
  }
}
