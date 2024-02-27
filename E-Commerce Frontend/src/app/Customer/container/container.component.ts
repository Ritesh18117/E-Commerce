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

  constructor() {
  }

  @ViewChild(ProductsListComponent) productListComponent!: ProductsListComponent;

  // For Closing Button in product Details Component
  onProductDetailsClose() {
    this.productListComponent.showProductDetails = false;
  }

}
