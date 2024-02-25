import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductVariationService {

  getProductVariationByIdURL = '/api/productVariation/getProductVariationById';
  addProductVariationURL = '/api/productVariation/addProductVariation';
  deleteByProductIdAndSizeURL = "/api/productVariation/deleteByProductVariationId";
  
  constructor(private http :HttpClient) { }

  getProductVariationById(id:number):Observable<any> {
    return this.http.get<any>(`${this.getProductVariationByIdURL}/${id}`)
  }

  addProductVariation(token:string,productVariation:any){
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.post<any>(this.addProductVariationURL, productVariation, { headers });
  }

  deleteByProductIdAndSize(token:string,productId:number,size:string){
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.delete<any>(`${this.deleteByProductIdAndSizeURL}/${productId}/${size}`, { headers });
  }
}
