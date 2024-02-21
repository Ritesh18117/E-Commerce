import { Component, ViewChild } from '@angular/core';
import { ProductsListComponent } from './products-list/products-list.component';
import { cardItem } from 'src/app/Models/cardItem';

@Component({
  selector: 'app-container',
  templateUrl: './container.component.html',
  styleUrls: ['./container.component.css']
})
export class ContainerComponent {

  // For Serach Component
  searched: string = '';
  //TEst comment

  // For Item add to card 
  cardItems: cardItem[] = [];

  constructor() {
    // For storing the Card items in Local Storage
    try {
      localStorage.getItem('cardItems');
    } catch {
      localStorage.setItem('cardItems', JSON.stringify(this.cardItems));
    }
  }

  @ViewChild(ProductsListComponent) productListComponent!: ProductsListComponent;

  // For Serach It 
  searchIt(event: string) {
    this.searched = event;
    console.log(this.searched + "From Contaner");
  }

  // For Closing Button in product Details Component
  onProductDetailsClose() {
    this.productListComponent.showProductDetails = false;
  }

  addItemToCard(cardItem: any) {
    console.log(cardItem);
  }
}
