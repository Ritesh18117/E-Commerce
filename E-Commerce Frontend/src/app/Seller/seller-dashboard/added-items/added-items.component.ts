import { Component } from '@angular/core';
import { SellerAuthService } from '../../auth/auth.service';
import { ProductServiceService } from 'src/app/Services/product-service.service';

@Component({
  selector: 'app-added-items',
  templateUrl: './added-items.component.html',
  styleUrls: ['./added-items.component.css']
})
export class AddedItemsComponent {
  
  token:any;
  myProducts:any;

  constructor(private _productService:ProductServiceService){}
  
  async ngOnInit(){
    this.token = sessionStorage.getItem('token');
    await this.getSellerProduct();
  }

  async getSellerProduct():Promise<any>{
    try{
        const data = await this._productService.getSellersProduct(this.token).toPromise();
        this.myProducts = data;
    } catch(error){
      console.error("Error",error);
    }
  }
  
}
