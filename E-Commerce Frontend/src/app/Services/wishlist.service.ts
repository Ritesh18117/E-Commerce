import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WishlistService {
  private addToWishlistURL = '/api/wishlist/addToWishlist'; // Update this with your actual URL

  constructor(private http: HttpClient) {}

  getMyWishlist(token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>('/api/wishlist/myWishlist', { headers }); // Update with your actual URL
  }

  removeItem(token: string, id: number): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete<any>(`/api/wishlist/removeItem/${id}`, { headers }); // Update with your actual URL
  }

  addToWishlist(wishlistItem: any, token: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post<any>(this.addToWishlistURL, wishlistItem, { headers });
  }
}
