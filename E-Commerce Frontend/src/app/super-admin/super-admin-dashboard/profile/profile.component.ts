import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { SuperAdminService } from 'src/app/Services/super-admin.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {

  token:any;
  profile:any;
  editProfile:boolean =  false;

  
  constructor(private toastr: ToastrService,private _superAdminService:SuperAdminService) { }

  async ngOnInit() {
    this.token = sessionStorage.getItem('token');
    await this.getSuperAdminProfile();
  }
  
  async getSuperAdminProfile(): Promise<any> {
    try {
      const data = await this._superAdminService.getSuperAdminProfile(this.token).toPromise();
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
    this._superAdminService.updateSuperAdminProfile(this.token,this.profile).subscribe(
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
