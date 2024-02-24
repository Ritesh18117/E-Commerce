import { Component, Input,EventEmitter, Output } from '@angular/core';
import { CategoryService } from 'src/app/Services/category.service';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css']
})
export class FilterComponent {

  categories:any;

  @Output() filterNotify: EventEmitter<number> = new EventEmitter();

  constructor(private categoryService:CategoryService){}

  ngOnInit(){
    this.getCategories();
  }

  getCategories(){
    this.categoryService.getAllCategory().subscribe(
      (data) =>{
        this.categories = data;        
      }, (error) =>{
        console.error("Error", error);
      }
    )
  }

  filter(categoryId:number){
    this.filterNotify.emit(categoryId);
  }
}
