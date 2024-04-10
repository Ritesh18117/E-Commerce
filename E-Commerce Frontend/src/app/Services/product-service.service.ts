import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductServiceService {

  private getProductApi = '/api/product/approvedProducts';
  private getSellersProductURL = '/api/product/myProducts';
  private addProductURL = '/api/product/addProduct';
  private getNotApprovedProductsURL = '/api/product/notApprovedProducts';
  private approveProductURL = '/api/product/approveProduct';
  private rejectProductURL = '/api/product/rejectProduct';
  private getProductByIdURL = "/api/product/getByProductId";
  private getMyProductVerifyListURL = "/api/product/getMyProductVerifyList";
  private revokeProductVerifyURL = "/api/product/revokeProductVerify";
  private searchURL = "/api/product/search";
  private updateProductURL = "/api/product/updateProduct";
  private getAllMyRejectedProductsURL = "/api/product/getAllMyRejectedProducts";

  constructor(private http: HttpClient) { }

  getApprovedProducts(): Observable<any> {
    return this.http.get(`${this.getProductApi}`);
  }

  getSellersProduct(token:string):Observable<any> {
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get(this.getSellersProductURL, { headers });
  }

  addProduct(token:string, product:any):Observable<any> {
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.post<any>(this.addProductURL, product, { headers });
  }

  addProductFromData(token:string, formData:any):Observable<any> {
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.post<any>(this.addProductURL, formData, { headers });
  }

  getNotApprovedProducts(token:string):Observable<any> {
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(this.getNotApprovedProductsURL, { headers });
  }

  approveProduct(token:string,productId:number):Observable<any>{
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(`${this.approveProductURL}/${productId}`, { headers });
  }
  
  rejectProduct(token:string,productId:number):Observable<any>{
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(`${this.rejectProductURL}/${productId}`, { headers });
  }

  getProductById(productId:number):Observable<any>{
    return this.http.get(`${this.getProductByIdURL}/${productId}`);
  }

  getMyProductVerifyList(token:string){
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(this.getMyProductVerifyListURL, { headers });
  }

  revokeProductVerify(token:string,productId:number){
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(`${this.revokeProductVerifyURL}/${productId}`, { headers });
  }

  search(searchItem:string):Observable<any>{
    console.log('${searchURL}');
    console.log(`${searchItem}`);
    
    return this.http.get<any>(`${this.searchURL}/${searchItem}`); // /api/product/search/shoes
    
  }

  updateProduct(token:any,formData:any){
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.patch<any>(this.updateProductURL, formData, { headers });
  }

  getAllMyRejectedProducts(token:any){
    const headers = new HttpHeaders().set('Authorization',`Bearer ${token}`);
    return this.http.get<any>(this.getAllMyRejectedProductsURL, { headers });
  }
  
}
