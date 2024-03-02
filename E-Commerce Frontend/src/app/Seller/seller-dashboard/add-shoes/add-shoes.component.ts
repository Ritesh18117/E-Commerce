import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { CategoryService } from 'src/app/Services/category.service';
import { ProductServiceService } from 'src/app/Services/product-service.service';
import { SellerAuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-add-shoes',
  templateUrl: './add-shoes.component.html',
  styleUrls: ['./add-shoes.component.css']
})
export class AddShoesComponent {
  size:string = "";
  quantity:string = "";
  img:string = "";

  product = {
    name:"",
    category:{ id:""},
    gender:"",
    color:"",
    price:0,
    discount:0,
    margin:0,
    imageURL:"",
    description:"",
    images:[] as string[]
  }

      // Product data to send
    //   {
    //     "category":{
    //         "id":3
    //     },
    //     "name":"Men Checkered Round Neck Polyester Grey T-Shirt",
    //     "color":"Grey",
    //     "price":999,
    //     "discount":8,
    //     "margin":6,
    //     "gender":"men and Women",
    //     "description":"Grey T-Shirt",
    //     "imageURL":"https://rukminim2.flixcart.com/image/832/832/xif0q/t-shirt/p/j/c/s-ts12-vebnor-original-imagp6jcsgekgda4.jpeg?q=70&crop=false"
    // }

  categories:any;
  token:any;

  constructor(private toastr: ToastrService, private _categoryService:CategoryService, 
    private _productService:ProductServiceService) { }

  ngOnInit(){
    this.getCategory();
  }

  getCategory(){
    this._categoryService.getAllCategory().subscribe(
      (data) =>{
        console.log(data);
        this.categories = data;
      }, 
      (error) => {
        console.error("ERROR",error);
      }
    )
  }

  async onSubmit() {
    try {
      await console.log("Data before resetting:", this.product);
      this.token = sessionStorage.getItem('token');
      this._productService.addProduct(this.token,this.product).subscribe(
        (data) => {
          this.toastr.success('Product Added!!', 'Success', {
            timeOut: 500, // Toast will disappear after 0.5 seconds
          });
          this.resetProduct();
        }, (error) =>{
          console.error("ERROR", error);
          this.toastr.error('Error Adding product', 'Error', {
            timeOut: 500, // Toast will disappear after 0.5 seconds
          });
        }
      )
    } catch (error) {
      console.error("Error submitting form", error);
      this.toastr.error('Error submitting form', 'Error', {
        timeOut: 500, // Toast will disappear after 0.5 seconds
      });
    }
  }

  resetProduct(): void {
    this.product = {
      name: "",
      category: { id: "" },
      gender: "",
      color: "",
      price: 0,
      discount: 0,
      margin: 0,
      imageURL: "",
      description: "",
      images:[]
    };
  }

  addImage(){
    if(this.img !== ""){
      this.product.images.push(this.img);
      this.img = ""
    }
  }

  deleteImage(index:number){
    this.product.images.splice(index,1);
  }
  
}
