import { Component, ViewChild } from '@angular/core';
import { ProductsListComponent } from './products-list/products-list.component';
import { cardItem } from 'src/app/Models/cardItem';

@Component({
  selector: 'app-container',
  templateUrl: './container.component.html',
  styleUrls: ['./container.component.css']
})
export class ContainerComponent {

  // For Item add to card 
  cardItems: cardItem[] = [];
  searchValue:string = "";

  


  searchReceive(searchValue:string){
    this.searchValue = searchValue; 
  }



  role:string = "" ;
  ngAfterContentChecked(){
    if(sessionStorage.getItem('role') !== null){
      const storedRole = sessionStorage.getItem('role');
      if(storedRole !== null){
        this.role = storedRole;
      }else{
        console.log("Error from nav-comp!!!");
        this.role = "";
      }
    }
  }

}
