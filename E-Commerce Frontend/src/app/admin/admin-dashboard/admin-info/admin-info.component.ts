import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-info',
  templateUrl: './admin-info.component.html',
  styleUrl: './admin-info.component.css'
})
export class AdminInfoComponent {
  
  token:any;
  editProfile:boolean = false;
  profile:any;

  editProfileMethod(){
    this.editProfile = !this.editProfile;
  }

  onSubmit(){

  }
}
