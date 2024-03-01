import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { CategoryService } from 'src/app/Services/category.service';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrl: './add-category.component.css'
})
export class AddCategoryComponent {

  category = {
    categoryName : "",
    description : ""
  };
  token: any;
  showCategoryButton:boolean = true;
  allCategory:any;

  constructor(private _categoryService:CategoryService, private _toastr:ToastrService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
  }

  showCategory(){
    this._categoryService.getAllCategory().subscribe(
      (data) =>{
        this.allCategory = data;
        this.showCategoryButton = !this.showCategoryButton;
      }, (error) =>{
        console.error("Error!",error);
      }
    )
  }

  hideCategory(){
    this.allCategory = null;
    this.showCategoryButton = !this.showCategoryButton;
  }

  deleteCategory(id:number,index:number){
    this._categoryService.deleteCategoryById(this.token,id).subscribe(
      (data) =>{
        console.log(data);
        this.allCategory.splice(index,1);
        this._toastr.success("Category Deleted Successfully!","Success");
      }, (error) =>{
        console.error("Error",error);
        this._toastr.error("Categorgy Deletion Error!","Error");        
      }
    )
  }

  onSubmit(){
    if(this.category.categoryName){
      this._categoryService.addCategory(this.token,this.category).subscribe(
        (data) =>{
          console.log(data);
          this._toastr.success("Category Added Successfully!!","Success");
          this.resetCategory();
          this.showCategory();
        }, (error) =>{
          console.error("Error",error);
          this._toastr.error("Error Posting Data!!",error);
        }
      )
    } else{
      this._toastr.error("Please provide Category Name!","Error");
    }
  }

  resetCategory(){
    this.category.categoryName = "";
    this.category.description = "";
  }
}
