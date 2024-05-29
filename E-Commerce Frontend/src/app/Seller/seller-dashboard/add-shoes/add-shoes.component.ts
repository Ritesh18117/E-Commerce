// import { Component } from '@angular/core';
// import { ToastrService } from 'ngx-toastr';
// import { CategoryService } from 'src/app/Services/category.service';
// import { ProductServiceService } from 'src/app/Services/product-service.service';
// import { SellerAuthService } from '../../auth/auth.service';

// @Component({
//   selector: 'app-add-shoes',
//   templateUrl: './add-shoes.component.html',
//   styleUrls: ['./add-shoes.component.css']
// })
// export class AddShoesComponent {
//   size:string = "";
//   quantity:string = "";
//   img:string = "";

//   singleImage: File | null = null;
//   multipleImages: File[] = [];

//   product = {
//     name:"",
//     category:{ id:""},
//     gender:"",
//     color:"",
//     price:0,
//     discount:0,
//     margin:0,
//     image:File,
//     description:"",
//     images:[] as File[]
//   }

//   categories:any;
//   token:any;

//   basicColors = [
//     { name: "Red", value: "#FF0000" },
//     { name: "Green", value: "#00FF00" },
//     { name: "Blue", value: "#0000FF" },
//     { name: "Black", value: "#000000" },
//     { name: "White", value: "#FFFFFF" },
//     { name: "Brown", value: "#A52A2A" },
//     { name: "Yellow", value: "#FFFF00" },
//     { name: "Orange", value: "#FFA500" },
//     { name: "Purple", value: "#800080" },
//     { name: "Gray", value: "#808080" },
//     // Add more basic colors as needed
//   ];

//   constructor(private toastr: ToastrService, private _categoryService:CategoryService, 
//     private _productService:ProductServiceService) { }

//   ngOnInit(){
//     this.getCategory();
//   }

//   getCategory(){
//     this._categoryService.getAllCategory().subscribe(
//       (data) =>{
//         console.log(data);
//         this.categories = data;
//       }, 
//       (error) => {
//         console.error("ERROR",error);
//       }
//     )
//   }


//   async onSubmit() {
//     try {

//       // product = {
//       //   image:File,
//       //   images:[] as File[]
//       // }

//       const formData = new FormData();
  
//       // Append normal fields
//       formData.append('name', this.product.name);
//       formData.append('gender', this.product.gender);
//       formData.append('price', this.product.price.toString());
//       formData.append('discount', this.product.discount.toString());
//       formData.append('margin', this.product.margin.toString());
//       formData.append('color', this.product.color);
//       formData.append('description', this.product.description);

//       if (this.product.category.id) {
//         formData.append('category', this.product.category.id);
//       }
      
//       // Append image files
//       if (this.singleImage) {
//         formData.append('image', this.singleImage);
//       }

//       // Append multiple images
//       this.multipleImages.forEach((file, index) => {
        
//         formData.append(`images`, file, file.name);
//       });

//       await console.log("Data before resetting:", this.product);
//       this.token = sessionStorage.getItem('token');
//       this._productService.addProductFromData(this.token,formData).subscribe(
//         (data) => {
//           this.toastr.success('Product Added!!', 'Success', {
//             timeOut: 500, // Toast will disappear after 0.5 seconds
//           });
//           this.resetProduct();
//         }, (error) =>{
//           console.error("ERROR", error);
//           this.toastr.error('Error Adding product', 'Error', {
//             timeOut: 500, // Toast will disappear after 0.5 seconds
//           });
//         }
//       )
//     } catch (error) {
//       console.error("Error submitting form", error);
//       this.toastr.error('Error submitting form', 'Error', {
//         timeOut: 500, // Toast will disappear after 0.5 seconds
//       });
//     }
//   }

//   onSingleFileSelected(event: any) {
//     this.singleImage = event.target.files ? event.target.files[0] : null;
//     this.product.image = event.target.files[0];
//     console.log('Selected single image:', this.singleImage);
//   }

//   onMultipleFilesSelected(event: any) {
//     this.multipleImages = event.target.files ? Array.from(event.target.files) : [];
//     this.product.images = Array.from(event.target.files);
//     console.log('Selected multiple images:', this.multipleImages);
//   }

//   resetProduct(): void {
//     this.product = {
//       name: "",
//       category: { id: "" },
//       gender: "",
//       color: "",
//       price: 0,
//       discount: 0,
//       margin: 0,
//       image:File,
//       description: "",
//       images: []
//     };
//   }

//   deleteImage(index:number){
//     this.product.images.splice(index,1);
//   }

//   selectBasicColor(event: any) {
//     const colorValue = event.target.value;
//     this.product.color = colorValue;
//   }

// }
import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { CategoryService } from 'src/app/Services/category.service';
import { ProductServiceService } from 'src/app/Services/product-service.service';

@Component({
  selector: 'app-add-shoes',
  templateUrl: './add-shoes.component.html',
  styleUrls: ['./add-shoes.component.css']
})
export class AddShoesComponent {
  size: string = "";
  quantity: string = "";
  img: string = "";
  

  singleImage: File | null = null;
  multipleImages: File[] = [];
  imageError: boolean = false;
  imageErrors: boolean = false;


  product = {
    name: "",
    category: { id: "" },
    gender: "",
    color: "",
    price: 0,
    discount: 0,
    margin: 0,
    image: File,
    description: "",
    images: [] as File[]
  }

  categories: any;
  token: any;

  basicColors = [
    { name: "Red", value: "#FF0000" },
    { name: "Green", value: "#00FF00" },
    { name: "Blue", value: "#0000FF" },
    { name: "Black", value: "#000000" },
    { name: "White", value: "#FFFFFF" },
    { name: "Brown", value: "#A52A2A" },
    { name: "Yellow", value: "#FFFF00" },
    { name: "Orange", value: "#FFA500" },
    { name: "Purple", value: "#800080" },
    { name: "Gray", value: "#808080" },
    // Add more basic colors as needed
  ];

  constructor(private toastr: ToastrService, private _categoryService: CategoryService, 
    private _productService: ProductServiceService) { }

  ngOnInit() {
    this.getCategory();
  }

  getCategory() {
    this._categoryService.getAllCategory().subscribe(
      (data) => {
        console.log(data);
        this.categories = data;
      },
      (error) => {
        console.error("ERROR", error);
      }
    )
  }

  async onSubmit() {
    try {
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

      this.token = sessionStorage.getItem('token');
      this._productService.addProductFromData(this.token, formData).subscribe(
        (data) => {
          this.toastr.success('Product Added!!', 'Success', {
            timeOut: 500, // Toast will disappear after 0.5 seconds
          });
          this.resetProduct();
        }, (error) => {
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
    const file = event.target.files ? event.target.files[0] : null;
    if (file && file.size > 10 * 1024) {
      this.imageError = true;
      this.singleImage = null;
    } else {
      this.imageError = false;
      this.singleImage = file;
      this.product.image = file;
    }
    console.log('Selected single image:', this.singleImage);
  }
file:any;
maxFiles: number = 4;

onMultipleFilesSelected(event: any) {
    const files = event.target.files ? Array.from(event.target.files) : [];
    const validFiles: File[] = [];
    const oversizedFiles: File[] = [];
    
    files.forEach(file => {
        if (this.file.size > 30 * 1024) {
            oversizedFiles.push(this.file);
        } else {
            validFiles.push(this.file);
        }
    });

    if (oversizedFiles.length > 0) {
        this.imageErrors = true;
        this.toastr.error('One or more images exceed 30 KB', 'Error', {
            timeOut: 500, // Toast will disappear after 0.5 seconds
        });
    } else {
        this.imageErrors = false;
    }

    if (validFiles.length > this.maxFiles) {
        this.imageErrors = true;
        this.toastr.error(`You can only upload up to ${this.maxFiles} images`, 'Error', {
            timeOut: 500, // Toast will disappear after 0.5 seconds
        });
        this.multipleImages = validFiles.slice(0, this.maxFiles);
    } else {
        this.multipleImages = validFiles;
    }

    this.product.images = this.multipleImages;

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
      image: File,
      description: "",
      images: []
    };
  }

  deleteImage(index: number) {
    this.product.images.splice(index, 1);
  }

  selectBasicColor(event: any) {
    const colorValue = event.target.value;
    this.product.color = colorValue;
  }
}
