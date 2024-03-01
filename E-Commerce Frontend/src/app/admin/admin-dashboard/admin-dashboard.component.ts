import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {

  adminInfo:boolean = true;
  verifySeller:boolean = false;
  verifyProduct:boolean = false;
  verifiedProduct:boolean = false;
  verifiedSeller:boolean = false;
  addCatergory:boolean = false;

  renderAdminInfo(){
    this.adminInfo = true;
    this.verifySeller = false;
    this.verifyProduct = false;
    this.verifiedProduct = false;
    this.verifiedSeller = false;
    this.addCatergory = false;
  }

  renderverifySeller(){
    this.verifySeller = true;
    this.verifiedSeller = false;
    this.adminInfo = false;
    this.verifyProduct = false;
    this.verifiedProduct = false;
    this.addCatergory = false;
  }

  renderVerifiedSeller(){
    this.verifiedSeller = true;
    this.adminInfo = false;
    this.verifySeller = false;
    this.verifyProduct = false;
    this.verifiedProduct = false;
    this.addCatergory = false;
  }

  renderVerifyProduct(){
    this.verifyProduct = true;
    this.verifiedSeller = false;
    this.adminInfo = false;
    this.verifySeller = false;
    this.verifiedProduct = false;
    this.addCatergory = false;
  }

  renderVerifiedProduct(){
    this.verifiedProduct = true;
    this.verifiedSeller = false;
    this.verifyProduct = false;
    this.adminInfo = false;
    this.verifySeller = false;
    this.addCatergory = false;
  }

  renderAddCategory(){
    this.addCatergory = true;
    this.verifiedProduct = false;
    this.verifiedSeller = false;
    this.verifyProduct = false;
    this.adminInfo = false;
    this.verifySeller = false;
  }
}
