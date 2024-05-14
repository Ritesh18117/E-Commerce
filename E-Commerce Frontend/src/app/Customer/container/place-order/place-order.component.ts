import { Component, ViewChild } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Address } from 'src/app/Models/address';
import { CartItemService } from 'src/app/Services/cart-item.service';
import { CustomerAddressService } from 'src/app/Services/customer-address.service';
import { OrderService } from 'src/app/Services/order.service';
import { AuthService } from 'src/app/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-place-order',
  templateUrl: './place-order.component.html',
  styleUrls: ['./place-order.component.css']
})
export class PlaceOrderComponent {
  @ViewChild('radio1') radio1: any;
  @ViewChild('radio2') radio2: any;
  @ViewChild('radio3') radio3: any;
  cardItems:any;
  token:any;
  myAddresses:any;
  
  total:number = 0;
  discount:number = 0;
  delivery:number = 49;
  totalQuantity:number = 0;
  selectedAddressIndex: number = 0;

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
  selectedElement: HTMLElement | null = null;

  
  selectRadio(radio: any) {
    radio.checked = true;
    this.clearSelectedRadios();
    radio.parentElement.parentElement.classList.add('selected-radio');
  }
  clearSelectedRadios() {
    const radios = document.querySelectorAll('.selected-radio');
    radios.forEach((radio: any) => {
      radio.classList.remove('selected-radio');
    });
  }

  // showForm Varible
  showForm:boolean = false;


  message:string = "";
  forMessage:string = "";
  selectedAddress:any = null;
  selectedPaymentMethod = "Cash On Delivery";

  constructor(private _cartItemService:CartItemService,
    private _authService:AuthService,
    private _customerAddressService:CustomerAddressService,
    private _orderService:OrderService,
    private _toastr:ToastrService,
    private router: Router){
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
      },
      (error) =>{
        console.error("Error Found!!");
      }
    )
  }

  getCustomerAddress() {
    const token = this._authService.getToken();

    if (token) {
      this._customerAddressService.getAddress(token).subscribe(
        (data) => {
          this.myAddresses = data;
          this.selectedAddress = data[0];
        },
        (error) => {
          console.error('Error fetching data:', error);
        }
      );
    } else {
      console.error('Token not available.');
    }
  }

  selectAddress(address:Address, index:number){
    this.selectedAddressIndex = index;
    this.selectedAddress = address;
  }

  selectedPayment(paymentMethod:string){
    this.selectedPaymentMethod = paymentMethod;
  }

  showAddressForm(){
    this.showForm = !this.showForm
  }
  
  onAddressSubmit(){
    const token = this._authService.getToken();
    if(token){
      this._customerAddressService.postAddress(this.addAddress,token).subscribe(
        async (data) => {
          await console.log(data);
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

  placeOrder(){
    if(this.selectedAddress == undefined){
      this._toastr.warning("Warning","Please Select Address!",{
        timeOut:500
      });
    }
    if(this.selectedPaymentMethod == undefined){
      this._toastr.warning("Warning","Please Select Payment Method!",{
        timeOut:500
      });
    }
    console.log(this.selectedAddress);
    console.log(this.selectedPaymentMethod); 
    const placeOrder: { productVariations: any[], address: { id: any }, comment: string } = {
      productVariations: this.cardItems.map((item: { productVariation: { id: any; }; quantity: any; }) => ({
        id: item.productVariation.id,
        quantity: item.quantity
      })),
      address: {
        id: this.selectedAddress.id
      },
      comment: ""
    };

    this._orderService.placeOrder(this.token,placeOrder).subscribe(
      (data) =>{
        this._toastr.success("success","Order Placed Successfully !!",{
          timeOut:1000
        });
        this.getCartItem();
        this.router.navigate(['/order-placed']);
      },
      (error) =>{
        console.error(error);
        this._toastr.error("error","Order Placed Successfully !!",{
          timeOut:1000
        })
      }
    )
    
    console.log(placeOrder);
    
  }    


  convertToImage(image:any) {
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
}
