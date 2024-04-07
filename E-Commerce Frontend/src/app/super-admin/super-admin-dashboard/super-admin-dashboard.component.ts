import { Component } from '@angular/core';

@Component({
  selector: 'app-super-admin-dashboard',
  templateUrl: './super-admin-dashboard.component.html',
  styleUrl: './super-admin-dashboard.component.css'
})
export class SuperAdminDashboardComponent {

  profile:boolean = true;
  approveSeller:boolean = false;
  approvedSeller:boolean = false;

  constructor(){}

  renderProfile(){
    this.profile = true;
    this.approveSeller = false;
    this.approvedSeller = false;
  }
  
  renderApproveSeller(){
    this.approveSeller = true;
    this.profile = false;
    this.approvedSeller = false;
  }

  renderApprovedSeller(){
    this.approvedSeller = true;
    this.profile = false;
    this.approveSeller = false;
  }

}
