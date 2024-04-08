import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { SuperAdminService } from 'src/app/Services/super-admin.service';

@Component({
  selector: 'app-approved-admin',
  templateUrl: './approved-admin.component.html',
  styleUrl: './approved-admin.component.css'
})
export class ApprovedAdminComponent {

  token:any;
  ActiveAdmins:any;

  constructor(private toastr:ToastrService,private superAdminService:SuperAdminService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getActiveAdmin();
  }

  getActiveAdmin(){
    this.superAdminService.getActiveAdmin(this.token).subscribe(
      (data) =>{
        this.ActiveAdmins = data;
      }, (error) =>{
        console.error("ERROR", error);
      }
    );
  }

  makeAdminInActive(adminId:number){
    this.superAdminService.makeAdminInActive(this.token,adminId).subscribe(
      (data) =>{
        this.toastr.success("Seller Rejected Successfully!!", "Success", {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        })
        this.ngOnInit();
      }, (error) => {
        console.error("Error Approving", error);
        this.toastr.error('Product Rejection Error!!', 'Error', {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      }
    );
  }
}
