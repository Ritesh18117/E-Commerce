import { Component } from '@angular/core';

@Component({
  selector: 'app-super-admin-dashboard',
  templateUrl: './super-admin-dashboard.component.html',
  styleUrl: './super-admin-dashboard.component.css'
})
export class SuperAdminDashboardComponent {

  profile:boolean = true;
  approveAdmin:boolean = false;
  approvedAdmin:boolean = false;

  constructor(){}

  renderProfile(){
    this.profile = true;
    this.approveAdmin = false;
    this.approvedAdmin = false;
  }
  
  renderApproveSeller(){
    this.approveAdmin = true;
    this.profile = false;
    this.approvedAdmin = false;
  }

  renderApprovedSeller(){
    this.approvedAdmin = true;
    this.profile = false;
    this.approveAdmin = false;
  }

}
