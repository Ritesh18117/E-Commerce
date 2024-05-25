import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private getAllReviewURL = '/api/review/getAll';
  private getReviewByCustomerIdURL = "/api/review/getReviewsByCustomerId";
  private getReviewByProductIdURL = "/api/review/getReviewByProductId";
  private addReviewURL = '/api/review/addReview';
  private deleteReviewByProductIdURL = '/api/review/deleteReview';

  constructor(private http:HttpClient) { }

  getAllReview(): Observable<any> {
    return this.http.get(`${this.getAllReviewURL}`);
  }
  // getAllReview(token:string): Observable<any> {
  //   const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  //   return this.http.get(`${this.getAllReviewURL}`);
  // }
  // getReviewByCustomerId(reviewId:number):Observable<any>{
  //   return this.http.get(`${this.getReviewByCustomerIdURL}/${reviewId}`);
  // }

  getReviewByCustomerId(token: string): Observable<any> {
    
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(this.getReviewByCustomerIdURL, { headers });
  }
  getReviewByProductId(productId: number): Observable<any> {
    return this.http.get<any>(`${this.getReviewByProductIdURL}/${productId}`);
  }

  addReview(review:any, token:string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post<any>(this.addReviewURL, review, { headers });
  }

  deleteReviewByProductId(id:number,token:string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete<any>(`${this.deleteReviewByProductIdURL}/${id}`, { headers });
  }
  
}
