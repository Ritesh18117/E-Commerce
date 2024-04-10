import { Component } from '@angular/core';
import { cardItem } from 'src/app/Models/cardItem';
import { CartItemService } from 'src/app/Services/cart-item.service';
import { ProductVariationService } from 'src/app/Services/product-variation.service';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-card',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CardComponent {
  cardItems:any[] = [];
  token:any;

  total:number = 0;
  discount:number = 0;
  delivery:number = 49;
  totalQuantity:number = 0;

  constructor(private _cartItemService:CartItemService,private _productVariationService:ProductVariationService,private _authService:AuthService){
    this.token = this._authService.getToken();
  }

  ngOnInit():any{
    this.getCartItem();
  }

  getCartItem():any{
    this._cartItemService.getMyCart(this.token).subscribe(
      (data) => {
        this.cardItems = data;
        
        this.discount = 0;
        this.total = 0;
        this.totalQuantity = 0;
        for(let cardItem of this.cardItems){
          this.discount += ((cardItem.product.discount * cardItem.product.price)/100) * cardItem.quantity;
          this.total += cardItem.product.price * cardItem.quantity;
          this.totalQuantity += cardItem.quantity;
        }
      },
      (error) =>{
        console.error("Error Found!!");
      }
    )
  }

  removeItem(id: number): any {
    this._cartItemService.removeItem(this.token, id).subscribe(
      (data) => {
        this.getCartItem();
      },
      (error) => {
        console.error("Error Found!!", error); // Log the error details
      }
    );
  }

  async plus(item: any) {
    try {
      let productVariation: any = await this._productVariationService.getProductVariationById(item.productVariation.id)
        .toPromise();
  
      if (productVariation.quantity > 0 && productVariation.quantity > item.quantity) {
        this._cartItemService.addQuantity(item.id, this.token).subscribe(
          (data) =>{
            console.log("Quantity increased!", data);
            item.quantity++;
            this.discount += ((item.product.discount * item.product.price)/100);
            this.total += item.product.price;
            this.totalQuantity++;
          },
          (error) =>{
            console.error("Error",error);
          }
        )
      } else {
        console.error("Product quantity is not greater than 0");
      }
    } catch (error) {
      console.error("Error Found!!", error);
    }
  }
  

  async minus(item:any){
    try {
      let productVariation: any = await this._productVariationService.getProductVariationById(item.productVariation.id)
        .toPromise();
  
      if (item.quantity > 1) {
        this._cartItemService.substarctQuantity(item.id, this.token).subscribe(
          (data) =>{
            console.log("Quantity decreased!", data);
            item.quantity--;
            this.discount -= ((item.product.discount * item.product.price)/100);
            this.total -= item.product.price;
            this.totalQuantity--;
          },
          (error) =>{
            console.error("Error",error);
          }
        )
      } else {
        console.error("Product quantity is not greater than 0");
      }
    } catch (error) {
      console.error("Error Found!!", error);
    }
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
