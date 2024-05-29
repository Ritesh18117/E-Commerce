import { Component } from '@angular/core';
import { WishlistService } from 'src/app/Services/wishlist.service';
import { ProductVariationService } from 'src/app/Services/product-variation.service';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css']
})
export class WishlistComponent {
  wishlistItems: any[] = [];
  token: any;

  constructor(
    private _wishlistService: WishlistService,
    private _productVariationService: ProductVariationService,
    private _authService: AuthService
  ) {
    this.token = this._authService.getToken();
  }

  ngOnInit(): any {
    this.getWishlistItems();
  }

  getWishlistItems(): any {
    this._wishlistService.getMyWishlist(this.token).subscribe(
      (data) => {
        this.wishlistItems = data;
      },
      (error) => {
        console.error("Error Found!!");
      }
    )
  }

  removeItem(id: number): any {
    this._wishlistService.removeItem(this.token, id).subscribe(
      (data) => {
        this.getWishlistItems();
      },
      (error) => {
        console.error("Error Found!!", error);
      }
    );
  }

  addToWishlist(wishlistItem: any): any {
    this._wishlistService.addToWishlist(wishlistItem, this.token).subscribe(
      (data) => {
        console.log("Item added to wishlist!", data);
        this.getWishlistItems();
      },
      (error) => {
        console.error("Error Found!!", error);
      }
    );
  }

  async convertToImage(image: any) {
    const blob = this.base64toBlob(image, 'image/png'); 
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
