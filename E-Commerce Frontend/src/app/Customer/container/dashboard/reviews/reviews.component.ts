// import { Component } from '@angular/core';
// import { ReviewService } from 'src/app/Services/review.service';
// import { AuthService } from 'src/app/auth/auth.service';

// @Component({
//   selector: 'app-reviews',
//   // standalone: true,
//   // imports: [],
//   templateUrl: './reviews.component.html',
//   styleUrl: './reviews.component.css'
// })
// export class ReviewsComponent {
  
// constructor(
//   private _authService: AuthService,
//   private reviewService:ReviewService){}
  
  
//   reviews: any;
//   ngOnInit() {
//     this.getMyReviews();
//   }
  
  
//   getMyReviews(){
//     const token = this._authService.getToken();

//     if (token) {
//       // Include the token in the API request headers
//       this. reviewService.getReviewByCustomerId(token).subscribe(
//         (data) => {
//           this.reviews = data;
          
//           // Process the data as needed
//         },
//         (error) => {
//           console.error('Error fetching data:', error);
//         }
//       );
//     } else {
//       console.error('Token not available.');
//     }
//   }
// }
import { Component } from '@angular/core';
import { ReviewService } from 'src/app/Services/review.service';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.css']
})
export class ReviewsComponent {

  reviews: any;

  constructor(
    private _authService: AuthService,
    private reviewService: ReviewService
  ) {}

  ngOnInit() {
    this.getMyReviews();
  }

  getMyReviews() {
    const token = this._authService.getToken();

    if (token) {
      this.reviewService.getReviewByCustomerId(token).subscribe(
        (data) => {
          this.reviews = data;
          // Process the data as needed
        },
        (error) => {
          console.error('Error fetching data:', error);
        }
      );
    } else {
      console.error('Token not available.');
    }
  }

  deleteReviewByProductId(productId: number) {
    const token = this._authService.getToken();

    if (token) {
      this.reviewService.deleteReviewByProductId(productId, token).subscribe(
        (response) => {
          console.log('Review deleted:', response);
          // Optionally, refresh the reviews list after deletion
          this.getMyReviews();
        },
        (error) => {
          console.error('Error deleting review:', error);
        }
      );
    } else {
      console.error('Token not available.');
    }
  }
}
