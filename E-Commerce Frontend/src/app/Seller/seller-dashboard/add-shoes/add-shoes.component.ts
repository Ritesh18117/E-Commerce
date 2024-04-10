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

  singleImage: File | null = null;
  multipleImages: File[] = [];

  product = {
    name:"",
    category:{ id:""},
    gender:"",
    color:"",
    price:0,
    discount:0,
    margin:0,
    image:File,
    description:"",
    images:[] as File[]
  }

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

      // product = {
      //   image:File,
      //   images:[] as File[]
      // }

      const formData = new FormData();
  
      // Append normal fields
      formData.append('name', this.product.name);
      formData.append('gender', this.product.gender);
      formData.append('price', this.product.price.toString());
      formData.append('discount', this.product.discount.toString());
      formData.append('margin', this.product.margin.toString());
      formData.append('color', this.product.color);
      formData.append('description', this.product.description);

      if (this.product.category.id) {
        formData.append('category', this.product.category.id);
      }
      
      // Append image files
      if (this.singleImage) {
        formData.append('image', this.singleImage);
      }

      // Append multiple images
      this.multipleImages.forEach((file, index) => {
        
        formData.append(`images`, file, file.name);
      });

      await console.log("Data before resetting:", this.product);
      this.token = sessionStorage.getItem('token');
      this._productService.addProductFromData(this.token,formData).subscribe(
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

  onSingleFileSelected(event: any) {
    this.singleImage = event.target.files[0];
    this.product.image = event.target.files[0];
    console.log('Selected single image:', this.singleImage);
  }

  onMultipleFilesSelected(event: any) {
    this.multipleImages = Array.from(event.target.files);
    this.product.images = Array.from(event.target.files);
    console.log('Selected multiple images:', this.multipleImages);
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
      image:File,
      description: "",
      images: []
    };
  }

  deleteImage(index:number){
    this.product.images.splice(index,1);
  }

}
