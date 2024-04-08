import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private getAllCategoryURL = '/api/category/getAll';
  private getByCategoryIdURL = "/api/category/getProductByCategoryId";
  private addCategoryURL = "/api/category/addCategory";
  private deleteCategoryByIdURL = '/api/category/deleteCategoryById';

  constructor(private http:HttpClient) { }

  getAllCategory(): Observable<any> {
    return this.http.get(`${this.getAllCategoryURL}`);
  }

  getByCategoryId(categoryId:number):Observable<any>{
    return this.http.get(`${this.getByCategoryIdURL}/${categoryId}`);
  }

  addCategory(token:string,category:any):Observable<any>{
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post<any>(this.addCategoryURL,category,{ headers });
  }

  deleteCategoryById(token:string,categoryId:number):Observable<any>{
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete<any>(`${this.deleteCategoryByIdURL}/${categoryId}`,{ headers });
  }
  
}
