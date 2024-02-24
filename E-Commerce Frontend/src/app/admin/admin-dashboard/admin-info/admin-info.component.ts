// admin-info.component.ts
import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { AdminService } from 'src/app/Services/admin.service';

@Component({
  selector: 'app-admin-info',
  templateUrl: './admin-info.component.html',
  styleUrls: ['./admin-info.component.css'] 
})
export class AdminInfoComponent {
  token: any;
  editProfile = false; // Initialize to false
  profile: any;

  constructor(private adminService:AdminService,private toastr: ToastrService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getMyProfile();
  }

  async getMyProfile(){
    try {
      const data = await this.adminService.myProfile(this.token).toPromise();
      this.profile = data;
      console.log(data);
    } catch (error) {
      console.error("ERROR!!!", error);
    }
  }

  editProfileMethod() {
    this.editProfile = !this.editProfile;
  }

  onSubmit() {
    this.adminService.updateProfile(this.token,this.profile).subscribe(
      (data) => {
        console.log(data);
        this.profile = data;
        this.toastr.success('Profile Updated', 'Success');
        this.editProfile = !this.editProfile;
      }, (error) => {
        console.error("Error", error);
        this.toastr.error('Profile Updating Error', 'Success');
      }
    )
  }
}
