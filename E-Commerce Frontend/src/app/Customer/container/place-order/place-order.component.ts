import { Component } from '@angular/core';
import { Address } from 'src/app/Models/address';
import { CartItemService } from 'src/app/Services/cart-item.service';
import { CustomerAddressService } from 'src/app/Services/customer-address.service';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-place-order',
  templateUrl: './place-order.component.html',
  styleUrls: ['./place-order.component.css']
})
export class PlaceOrderComponent {

  cardItems:any;
  token:any;
  myAddresses:any;
  
  total:number = 0;
  discount:number = 0;
  delivery:number = 49;
  totalQuantity:number = 0;

  addAddress = {
    name: "",
    contact:"",
    address1:"",
    address2:"",
    city:"",
    state:"",
    country:"India",
    zipcode:""
  }

  // showForm Varible
  showForm:boolean = false;


  message:string = "";
  forMessage:string = "";
  selectedAddress:any = null;
  paymentMethod:string = "cod";

  constructor(private _cartItemService:CartItemService,private _authService:AuthService,private _customerAddressService:CustomerAddressService){
    this.token = this._authService.getToken();
  }

  ngOnInit():any{
    this.getCartItem();
    this.getCustomerAddress();
  }

  getCartItem():any{
    this._cartItemService.getMyCart(this.token).subscribe(
      (data) => {
        this.cardItems = data;
        this.discount = 0;
        this.total = 0;
        this.totalQuantity = 0;
        for(let cardItem of this.cardItems){
          this.discount += ((cardItem.product.discount * cardItem.product.price)/100) * cardItem.quantity;
          this.total += cardItem.product.price * cardItem.quantity;
          this.totalQuantity += cardItem.quantity;
        }
        console.log(this.cardItems);
        
      },
      (error) =>{
        console.error("Error Found!!");
      }
    )
  }
  getCustomerAddress() {
    const token = this._authService.getToken();

    if (token) {
      // Include the token in the API request headers
      this._customerAddressService.getAddress(token).subscribe(
        (data) => {
          this.myAddresses = data;
          console.log(this.myAddresses);
          
          // Process the data as needed
        },
        (error) => {
          console.error('Error fetching data:', error);
        }
      );
    } else {
      console.error('Token not available.');
    }
  }

  selectAddress(address:Address){
    this.selectedAddress = address;
    console.log(this.selectedAddress);
  }

  selectedPayment(paymentMethod:string){
    console.log(paymentMethod);
  }

  showAddressForm(){
    this.showForm = !this.showForm
  }

  onSubmit(){
    const token = this._authService.getToken();
    if(token){
      this._customerAddressService.postAddress(this.addAddress,token).subscribe(
        async (date) => {
          await console.log(date);
          this.ngOnInit();
        },
        (error) => {
          console.error("Posting Data Error", error);
        }
      )
    } else{
      console.error("Token Not Found");
    } 
    this.addAddress.address1 ="";
    this.addAddress.address2 = "";
    this.addAddress.city = "";
    this.addAddress.contact = "";
    this.addAddress.state = "";
    this.addAddress.zipcode = "";
    this.addAddress.name = "";
  }
}
