import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductVariationService {

  getProductVariationByIdURL = '/api/productVariation/getProductVariationById'
  
  constructor(private http :HttpClient) { }

  getProductVariationById(id:number):Observable<any> {
    return this.http.get<any>(`${this.getProductVariationByIdURL}/${id}`)
  }


}
