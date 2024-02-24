import { Component, Input,EventEmitter, Output } from '@angular/core';
import { CategoryService } from 'src/app/Services/category.service';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css']
})
export class FilterComponent {

  categories:any;

  constructor(private categoryService:CategoryService){}

  ngOnInit(){
    this.getCategories();
  }

  getCategories(){
    this.categoryService.getAllCategory().subscribe(
      (data) =>{
        this.categories = data;
        console.log(data);
        
      }, (error) =>{
        console.error("Error", error);
      }
    )
  }

  filter(category:string){
    
  }
}
