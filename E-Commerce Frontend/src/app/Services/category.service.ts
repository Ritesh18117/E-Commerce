import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private getAllCategoryURL = '/api/category/getAll';

  constructor(private http:HttpClient) { }

  getAllCategory(): Observable<any> {
    return this.http.get(`${this.getAllCategoryURL}`);
  }

}
