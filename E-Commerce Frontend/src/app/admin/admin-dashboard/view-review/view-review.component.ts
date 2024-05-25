// import { Component } from '@angular/core';
// import { ReviewService } from 'src/app/Services/review.service';


// @Component({
//   selector: 'app-view-review',
//   // standalone: true,
//   // imports: [],
//   templateUrl: './view-review.component.html',
//   styleUrl: './view-review.component.css'
// })
// export class ViewReviewComponent {
//   constructor(private reviewService:ReviewService){}
//   reviews: any;
//   ngOnInit() {
//     this.getReviews();
//   }
  
//   getReviews(){
//     this.reviewService.getAllReview().subscribe(
//       (data) =>{
//         this.reviews = data;        
//       }, (error) =>{
//         console.error("Error", error);
//       }
//     )
//   }

// }
import { Component, OnInit } from '@angular/core';
import { ReviewService } from 'src/app/Services/review.service';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-view-review',
  templateUrl: './view-review.component.html',
  styleUrls: ['./view-review.component.css']
})
export class ViewReviewComponent implements OnInit {
  reviews: any;

  constructor(
    private reviewService: ReviewService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.getReviews();
  }

  getReviews() {
    this.reviewService.getAllReview().subscribe(
      (data) => {
        this.reviews = data;
      },
      (error) => {
        console.error('Error', error);
      }
    );
  }

  deleteReview(reviewId: number) {
    const token = this.authService.getToken();

    if (token) {
      this.reviewService.deleteReviewByProductId(reviewId, token).subscribe(
        (response) => {
          console.log('Review deleted:', response);
          this.getReviews(); // Refresh the list after deletion
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
