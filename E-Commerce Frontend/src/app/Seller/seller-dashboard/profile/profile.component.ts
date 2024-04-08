import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { SellerService } from 'src/app/Services/seller.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  token:any;
  profile:any;
  editProfile:boolean =  false;

  constructor(private toastr: ToastrService,private _sellerService:SellerService) { }

  async ngOnInit() {
    this.token = sessionStorage.getItem('token');
    await this.getSellerProfile();
  }
  
  async getSellerProfile(): Promise<any> {
    try {
      const data = await this._sellerService.getSellerProfile(this.token).toPromise();
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
    this._sellerService.updateProfile(this.token,this.profile).subscribe(
      (data) =>{
        console.log(data);
        this.profile = data;
        this.editProfile = !this.editProfile;
        this.toastr.success('Product Added!!', 'Success', {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      },
      (error) =>{
        console.error("Error", error);
      }
    )
  }
}
