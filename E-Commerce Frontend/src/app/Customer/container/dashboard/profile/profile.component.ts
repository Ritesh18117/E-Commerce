import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { first } from 'rxjs';
import { CustomerProfileService } from 'src/app/Services/customer-profile.service';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  editProfile:boolean =  false;
  token:any;
  profile:any;

  constructor(private toastr: ToastrService,private _customerService:CustomerProfileService, ) { }

  async ngOnInit() {
    this.token = sessionStorage.getItem('token');
    await this.getSellerProfile();
  }
  
  async getSellerProfile(): Promise<any> {
    try {
      const data = await this._customerService.getCustomer(this.token).toPromise();
      this.profile = data;
      console.log(data);
    } catch (error) {
      console.error("ERROR!!!", error);
    }
  }

  editProfileMethod(){
    this.editProfile = !this.editProfile;
  }
  
  onSubmit(){
    this._customerService.updateCustomer(this.token,this.profile).subscribe(
      (data) =>{
        this.profile = data;
        this.editProfile = !this.editProfile;
        this.toastr.success('Product Added!!', 'Success');
      },
      (error) =>{
        console.error("Error", error);
      }
    )
  }
}
