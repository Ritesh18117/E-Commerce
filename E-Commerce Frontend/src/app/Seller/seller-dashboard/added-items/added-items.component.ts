import { Component } from '@angular/core';
import { ProductServiceService } from 'src/app/Services/product-service.service';
import { ProductVariationService } from 'src/app/Services/product-variation.service';
import { ToastrService } from 'ngx-toastr';
import { CategoryService } from 'src/app/Services/category.service';

@Component({
  selector: 'app-added-items',
  templateUrl: './added-items.component.html',
  styleUrls: ['./added-items.component.css']
})
export class AddedItemsComponent {

  token: any;
  myProducts: any;
  size_quant: Array<[string, string]> = [];
  size: string = "";
  quantity: string = "";

  activeProductId: any = null;
  editProductTag: boolean = false;

  singleImage: File | null = null;
  multipleImages: File[] = [];

  categories: any;
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

  // Define the product property
  product: any = {
    id: null,
    name: '',
    gender: '',
    price: 0,
    discount: 0,
    margin: 0,
    color: '',
    description: '',
    category: {
      id: null,
      categoryName: ''
    },
    image: '',
    images: []
  };

  constructor(private _categoryService: CategoryService,
              private toastr: ToastrService,
              private _productService: ProductServiceService,
              private _productVariationService: ProductVariationService) { }

  async ngOnInit() {
    this.token = sessionStorage.getItem('token');
    await this.getSellerProduct();
    this.getCategory();
  }

  async getSellerProduct(): Promise<any> {
    try {
      const data = await this._productService.getSellersProduct(this.token).toPromise();
      this.myProducts = data;
      console.log(this.myProducts);
    } catch (error) {
      console.error("Error", error);
    }
  }

  editProductId: string | null = null;
  editQuantityId: string | null = null;// ID of the product being edited
  showEditButton: boolean = true; // Flag to control visibility of the "Edit" button
  showQuantityButton: boolean = true;


  editProduct(productId: string) {
    this.editProductId = productId;
    this.showEditButton = false; // Hide the "Edit" button
  }

  editQuantity(productId: string) {
    this.editQuantityId = productId;
    this.showQuantityButton = false; // Hide the "Edit" button
  }


  cancelEdit() {
    this.editProductId = null;
    this.showEditButton = true; // Show the "Edit" button again
  }

  cancelQuantity() {
    this.editQuantityId= null;
    this.showQuantityButton = true; // Show the "Edit" button again
  }
  
  isEditingProduct(productId: string): boolean {
    return this.editProductId === productId;
  }
  isEditingQuantity(productId: string): boolean {
    return this.editQuantityId === productId;
  }

  parsedValues: { first: string; second: string }[] = [];
  addQuantityShowMethod(productId: any) {
    this.parsedValues = [];
    this._productService.getProductById(productId).subscribe(
      (data) => {
        let size_quanttity: Array<[string, string]> = data[0].size_quan;
        size_quanttity.forEach((tuple) => {
          const str = tuple.toString();
          const items: string[] = str.split(',');
          this.parsedValues.push({ first: items[0], second: items[1].trim() })
        });
      }, (error) => {
        console.error("Error", error);
      }
    )
    this.editQuantityId = productId;
    this.showQuantityButton = false; // Toggle the active product ID
  }

  addQuantity() {
    if (this.size !== "" && this.quantity !== "") {
      this.parsedValues.push({ first: this.size, second: this.quantity });
      this.size = "";
      this.quantity = "";
    }
  }

  deleteQuantity(index: number, productId: number, size: string) {
    // this.size_quant.splice(index, 1);
    console.log(size);

    this._productVariationService.deleteByProductIdAndSize(this.token, productId, size).subscribe(
      (data) => {
        console.log(data);
        this.toastr.success("Productvariation Deleted Successfully!!", "Success", {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      }, (error) => {
        console.error("ERROR", error);
        // this.toastr.error("Productvariation Deletion Error","Error");
      }
    );
    this.parsedValues.splice(index, 1);
  }

  Submit(product_id: number) {

    this.parsedValues.forEach((item) => {
      let tuple: [string, string] = [item.first, item.second];
      this.size_quant.push(tuple);
    });

    let productVariation: { // API DATA to Send
      product: {
        id: number;
      };
      size_quant: Array<[string, string]>; // Corrected syntax for the property
    } = {
      product: {
        id: product_id,
      },
      size_quant: this.size_quant,
    };

    this._productVariationService.addProductVariation(this.token, productVariation).subscribe(
      (data) => {
        this.toastr.success('Product Added!!', 'Success', {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      }, (error) => {
        console.error("ERROR", error);
        this.toastr.error('Error submitting form', 'Error', {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      }
    )
    this.size_quant = [];
    this.editQuantityId= null;
    this.showQuantityButton = true;  
  }

  getCategory() {
    this._categoryService.getAllCategory().subscribe(
      (data) => {
        this.categories = data;
      },
      (error) => {
        console.error("ERROR", error);
      }
    )
  }

  async onEditSubmit(product: any) {
    try {
      const formData = new FormData();
      // Append normal fields
      formData.append('id', product.id);
      formData.append('name', product.name);
      formData.append('gender', product.gender);
      formData.append('price', product.price.toString());
      formData.append('discount', product.discount.toString());
      formData.append('margin', product.margin.toString());
      formData.append('color', product.color);
      formData.append('description', product.description);
      if (product.category.id) {
        formData.append('category', product.category.id);
      }
      console.log(this.singleImage);
      console.log(this.multipleImages);
      
      // Append image files
      if (this.singleImage) {
        formData.append('image', this.singleImage);
      }
      // Append multiple images
      this.multipleImages.forEach((file, index) => {
        formData.append(`images`, file, file.name);
      });

      await console.log("Data before resetting:", product);
      this.token = sessionStorage.getItem('token');
      this._productService.updateProduct(this.token, formData).subscribe(
        (data) => {
          product = data;
          this.toastr.success('Product Added!!', 'Success');
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

  convertToImage(image: any) {
    const blob = this.base64toBlob(image, 'image/png'); // Change 'image/png' to the appropriate content type
    const urlCreator = window.URL || window.webkitURL;
    return urlCreator.createObjectURL(blob);
  }

  private base64toBlob(base64Data: string, contentType: string) {
    const byteCharacters = atob(base64Data);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    return new Blob([byteArray], { type: contentType });
  }

  onSingleFileSelected(event: any) {
    this.singleImage = event.target.files[0];
    this.product.image = event.target.files[0];
    console.log('Selected single image:', this.singleImage);
  }

  onMultipleFilesSelected(event: any) {
    this.multipleImages = Array.from(event.target.files);
    this.product.images = event.target.files;
    console.log('Selected multiple images:', this.multipleImages);
  }

  selectBasicColor(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    this.product.color = selectElement.value; // Set the selected color
    console.log('Selected color:', this.product.color);
  }

}