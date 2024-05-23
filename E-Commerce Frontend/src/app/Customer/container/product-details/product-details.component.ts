import { Component, EventEmitter, Input, Output } from '@angular/core';
import { cardItem } from 'src/app/Models/cardItem';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/auth/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CartItemService } from 'src/app/Services/cart-item.service';
import { ProductServiceService } from 'src/app/Services/product-service.service';
import { ReviewService } from 'src/app/Services/review.service';


//
import { ProductVariationService } from 'src/app/Services/product-variation.service';

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
    private _productService:ProductServiceService,
    private _productVariationService:ProductVariationService,
    private reviewService:ReviewService,
  ) {}






  // For Loading product when component is loaded
  product: any;
  role:any = "null";
  cardItem!: cardItem;
  productId: string | null = null;
  reviews: any ;
  sum:number=0;

  // Variables for card Items
  selectedSize: any;
  inStock:number = -1;
  selectedImage:any;
  quantity:number = 1;
  selectedImageIndex: number = 0;

  // Load product at first to show to details page
  ngOnInit() {
    this.getCurrentUrl();
    this.role = sessionStorage.getItem('role');
    this.productId = this.route.snapshot.paramMap.get('id');
    this.getProductById();
    this.getProductReviews(); 
   
    
  }
 

  parsedValues: { size: string; quantity: string }[] = [];
  getProductById(){
    if (this.productId !== null) {
      this._productService.getProductById(parseInt(this.productId)).subscribe(
        (data) => {
          this.product = data;
          this.selectedImage = data[0].product.image;
          let size_quanttity: Array<[string, string]> = data[0].size_quan;
          // Logic for Seperating the size and quant from size_quant
          size_quanttity.forEach((tuple) => {
            let first = "";
            let s = "";
            let comaOccurs = false;
            // size = tuple[0] + tuple[1];
            for (let i = 0; i < tuple.length; i++) {
              if(tuple[i] == ","){
                comaOccurs = true;
              }
              if(comaOccurs == false && tuple[i] != ","){
                first += tuple[i];
              }
              if(comaOccurs == true && tuple[i] !=" " && tuple[i] != ","){
                s += tuple[i];
              }
            }
            
            this.parsedValues.push({ size: first, quantity: s.trim() })
          });

        }, (error) => {
          console.error("Error", error);
          this.toastr.error("Product Finding Error!!","error", {
            timeOut: 500, // Toast will disappear after 0.5 seconds
          });
        }
      );
    }
  }

  // Size Selection
  pickSize(event: any) {
    const selectedSize = event.target.value;
    const selectedSizeObject = this.parsedValues.find(x => x.size === selectedSize);

    if (selectedSizeObject) {
      console.log("Hello");
      
      this.selectedSize = selectedSizeObject.size;
      console.log(selectedSizeObject.quantity);
      
      this.inStock = parseInt(selectedSizeObject.quantity);
      console.log(this.inStock);
    }
  }
  
  defaultSize(){
    this.selectedSize = this.parsedValues[0].size;
    this.inStock = parseInt(this.parsedValues[0].quantity);
  }
  

  // Add to card
  addToCard(product: any) {
    const token = this._authService.getToken();
    if (this._authService.isAuthenticate()) {
      if (!this.cardItem) {
        this.cardItem = product;
      }
      if (this.selectedSize) {
        const addToCartItem = {
          product_id: `${product.product.id}`,
          size: `${this.selectedSize}`,
          quantity: this.quantity,
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
        this.toastr.success('Product Added!!', 'Success', {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      } else {
        this.toastr.warning('Please Select Size!', 'warning', {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      }
    } else {
      this.toastr.warning('Please login First!!',"warning", {
        timeOut: 500, // Toast will disappear after 0.5 seconds
      });
    }
  }


  // Social Media Share
  currentUrl = "http://localhost:4200/"; // Replace with your actual page URL

  getCurrentUrl() {
    this.currentUrl += this.route.snapshot.url.map(segment => segment.path).join('/');
    console.log('Current URL:', this.currentUrl);
  }

  shareOnFacebook() {
    window.open(`https://www.facebook.com/sharer/sharer.php?u=${encodeURIComponent(this.currentUrl)}`, '_blank');
  }

  shareOnTwitter() {
    window.open(`https://twitter.com/intent/tweet?url=${encodeURIComponent(this.currentUrl)}`, '_blank');
  }

  shareOnInstagram() {
    window.open(`https://www.instagram.com/?url=${encodeURIComponent(this.currentUrl)}`, '_blank');
  }

  shareOnWhatsApp() {
    window.open(`whatsapp://send?text=${encodeURIComponent(this.currentUrl)}`, '_blank');
  }

  shareOnSnapchat() {
    window.open(`https://www.snapchat.com/add/${encodeURIComponent(this.currentUrl)}`, '_blank');
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

  plus() {
      if (this.inStock > 0 && this.inStock > this.quantity) {
           this.quantity++;
       }
  }
  minus(){
    this.quantity--;
  }

    
  getProductReviews() {
    if (this.productId !== null) {
      this.reviewService.getReviewByProductId(parseInt(this.productId)).subscribe(
        (data) => {
          this.reviews = data;
        },
        (error) => {
          console.error('Error', error);
        }
      );
    }
  }


  reviewDetails = {
    stars: 0, // Initialize stars to 0 for user selection
    comment: ''
  };
  onSubmitAddReview() {
    const token = this._authService.getToken();
  
    // Validation (optional)
    if (!this.reviewDetails.stars || this.reviewDetails.stars < 1 || this.reviewDetails.stars > 5) {
      console.error('Invalid star rating: Please select a rating between 1 and 5.');
      return;
    }
  
    if (!this.reviewDetails.comment || this.reviewDetails.comment.trim() === '') {
      console.error('Empty comment: Please enter a review.');
      return;
    }
  
    if (token) {
      const review = {
        product: { // Extract only the necessary properties from product
          id: this.productId,
          // Optionally include other relevant product information if needed
        }, // Assuming product is the product object
        stars: this.reviewDetails.stars,
        comment: this.reviewDetails.comment
      };
  
      this.reviewService.addReview(review, token).subscribe(
        (data) => {
          console.log('Review Added Successfully:', data);
          this.reviewDetails = { stars: 0, comment: '' }; // Clear the form
          // Optionally reload reviews or display a success message
        },
        (error) => {
          console.error("Error Adding Review:", error);
          // Handle errors (e.g., display an error message to the user)
        }
      );
    } else {
      console.error("Token Not Found");
      // Handle the case where the user is not authenticated
    }
  }

  
  
  
  // onSubmitAddReview() {
  //   const token = this._authService.getToken();

  //   // Validation (optional)
  //   if (!this.reviewDetails.stars || this.reviewDetails.stars < 1 || this.reviewDetails.stars > 5) {
  //     console.error('Invalid star rating: Please select a rating between 1 and 5.');
  //     return;
  //   }

  //   if (!this.reviewDetails.comment || this.reviewDetails.comment.trim() === '') {
  //     console.error('Empty comment: Please enter a review.');
  //     return;
  //   }

  //   if (token) {
  //     const review = {
  //       product: this.product,  // Assuming product.id is the product ID
  //       stars: this.reviewDetails.stars,
  //       comment: this.reviewDetails.comment
  //     };

  //     this.reviewService.addReview(review, token).subscribe(
  //       (data) => {
  //         console.log('Review Added Successfully:', data);
  //         this.reviewDetails = { stars: 0, comment: '' }; // Clear the form
  //         // Optionally reload reviews or display a success message
  //       },
  //       (error) => {
  //         console.error("Error Adding Review:", error);
  //         // Handle errors (e.g., display an error message to the user)
  //       }
  //     );
  //   } else {
  //     console.error("Token Not Found");
  //     // Handle the case where the user is not authenticated
  //   }
  // }

  // onSubmitAddReview(){
  //   const token = this._authService.getToken();
  //   if(token){
  //     this.reviewService.addReview(this.reviewDetails,token).subscribe(
  //       async (date) => {
  //         await console.log(date);
  //         this.ngOnInit();
  //       },
  //       (error) => {
  //         console.error("Posting Data Error", error);
  //       }
  //     )
  //   } else{
  //     console.error("Token Not Found");
  //   } 
  //   this.reviewDetails.product ="";
  //   this.reviewDetails.customer ="";
  //   this.reviewDetails.stars ="";
  //   this.reviewDetails.comment ="";
  // }

}