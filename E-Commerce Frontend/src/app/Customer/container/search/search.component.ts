import { Component, EventEmitter, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ProductServiceService } from 'src/app/Services/product-service.service';

@Component({
  selector: 'app-Search',
  templateUrl: './Search.component.html',
  styleUrls: ['./Search.component.css']
})
export class SearchComponent {
  
  searchValue:any;
  searched:boolean = false;

  @Output() searchItem = new EventEmitter<string>();

  onClick(){
    this.searchValue = this.searchValue.trim().toLowerCase();
    this.searchItem.emit(this.searchValue);
  }
}
