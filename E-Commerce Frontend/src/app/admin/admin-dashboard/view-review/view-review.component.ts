import { Component } from '@angular/core';
import { ReviewService } from 'src/app/Services/review.service';


@Component({
  selector: 'app-view-review',
  // standalone: true,
  // imports: [],
  templateUrl: './view-review.component.html',
  styleUrl: './view-review.component.css'
})
export class ViewReviewComponent {
  constructor(private reviewService:ReviewService){}
  reviews: any;
  ngOnInit() {
    this.getReviews();
  }
  
  getReviews(){
    this.reviewService.getAllReview().subscribe(
      (data) =>{
        this.reviews = data;        
      }, (error) =>{
        console.error("Error", error);
      }
    )
  }

}
