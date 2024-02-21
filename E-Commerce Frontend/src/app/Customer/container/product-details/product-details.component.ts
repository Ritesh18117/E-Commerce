import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ProductsListComponent } from '../products-list/products-list.component';
import { cardItem } from 'src/app/Models/cardItem';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/auth/auth.service';
import { Router } from '@angular/router';
import { CartItemService } from 'src/app/Services/cart-item.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css'],
})
export class ProductDetailsComponent {
  constructor(
    private toastr: ToastrService,
    private _authService: AuthService,
    private _cartItemService: CartItemService
  ) {}

  @Input() productListComp!: ProductsListComponent;

  // For Loading product when component is loaded
  product!: any;
  role:any = "null";
  cardItem!: cardItem;

  // Load product at first to show to details page
  ngOnInit() {
    this.product = this.productListComp.selectedProduct;    
    this.role = sessionStorage.getItem('role');
  }

  // For Closing Card
  @Output() onClose: EventEmitter<void> = new EventEmitter<void>();

  // For Closing Button (will be sent to container component)
  onClosed() {
    this.onClose.emit();
  }

  // Variables for card Items
  selectedSize: number = -1;

  // Size Selection
  pickSize(event: any) {
    this.selectedSize = event.target.value;
    console.log(this.selectedSize);
  }

  // Add to card
  addToCard(product: any) {
    const token = this._authService.getToken();
    if (this._authService.isAuthenticate()) {
      if (!this.cardItem) {
        this.cardItem = product;
      }
      if (this.selectedSize != -1) {
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
