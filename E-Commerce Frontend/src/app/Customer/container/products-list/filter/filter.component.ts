import { Component, Input,EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css']
})
export class FilterComponent {
  // Recieved From ProductList Component
  @Input() all:number = 0;
  @Input() inStock:number = 0;
  @Input() notInStock:number = 0;

  // Send message from Filter if select any radio button
  @Output() SelectedFilterRadioButton: EventEmitter<string> = new EventEmitter<string>();

  selectedRadioButton: string = 'all'

  // Filter radio Button
  onSelectedFilterRadioButton(){
    // console.log(this.selectedRadioButton);
    this.SelectedFilterRadioButton.emit(this.selectedRadioButton);
  }
}
