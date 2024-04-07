import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { SuperAdminService } from 'src/app/Services/super-admin.service';

@Component({
  selector: 'app-approve-admin',
  templateUrl: './approve-admin.component.html',
  styleUrl: './approve-admin.component.css'
})
export class ApproveAdminComponent {

  admins:any;
  token:any;

  constructor(private toastr: ToastrService,private superAdminService:SuperAdminService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getInActiveAdmin();
  }

  getInActiveAdmin(){
    this.superAdminService.getInActiveAdmin(this.token).subscribe(
      (data) =>{
        this.admins = data;
      }, (error) =>{
        console.error("ERROR", error);
      }
    );
  }

  makeAdminActive(adminId:number){
    this.superAdminService.makeAdminActive(this.token,adminId).subscribe(
      (data) =>{
        this.toastr.success("Seller Approved Successfully!!", "Success", {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        })
        this.ngOnInit();
      }, (error) => {
        console.error("Error Approving", error);
        this.toastr.error('Product Approval Error!!', 'Error', {
          timeOut: 500, // Toast will disappear after 0.5 seconds
        });
      }
    );
  }
}
