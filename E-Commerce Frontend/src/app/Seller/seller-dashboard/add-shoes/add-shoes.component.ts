import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { CategoryService } from 'src/app/Services/category.service';

@Component({
  selector: 'app-add-shoes',
  templateUrl: './add-shoes.component.html',
  styleUrls: ['./add-shoes.component.css']
})
export class AddShoesComponent {
  size:string = "";
  color:string = "";
  quantity:string = "";

  product = {
    name:"",
    category:"",
    gender:"",
    price:0,
    discountPrice:0,
    margin:0,
    size_quant: [] as Array<[string, string]>,
    imageURL:"",
    description:"",
  }

  categories:any;

  constructor(private toastr: ToastrService, private _categoryService:CategoryService) { }

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

  addQuantity() {
    if (this.size !== "" && this.quantity !== "") {
      let sizeQuant: [string, string] = [this.size, this.quantity];
      console.log(sizeQuant);
      this.product.size_quant.push(sizeQuant);
      this.size = "";
      this.quantity = "";

    }
  }

  async onSubmit() {
    try {

      let product = await console.log("Data before resetting:", this.product);
      console.log(product);

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
 
    // Product Variation 
  //   {
  //     "product":{
  //         "id":52
  //     },
  //     "size":"M",
  //     "quantity":12  
  // }
      
      this.product.name = "";
      this.product.gender = "";
      this.product.category = "";
      this.product.price = 0;
      this.product.discountPrice = 0;
      this.product.imageURL = "";
      this.product.description = "";
      this.product.size_quant = [];

      this.toastr.success('Product Added!!', 'Success');
    } catch (error) {
      console.error("Error submitting form", error);
      this.toastr.error('Error submitting form', 'Error');
    }
  }
  
}
