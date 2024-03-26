import { Component } from '@angular/core';

@Component({
  selector: 'app-documents',
  templateUrl: './documents.component.html',
  styleUrls: ['./documents.component.css']
})
export class DocumentsComponent {

  GSTfile:any;
  tradeLicenceFile:any;

  onFileSelected(event:any,index:number){    
    if(index == 1){
      this.GSTfile = event.target.files[0];
      console.log(this.GSTfile);
      
    }
    if(index == 2){
      this.tradeLicenceFile = event.target.files[0];
      console.log(this.tradeLicenceFile);
      
    }
  }

  onSubmit(){
    console.log(this.GSTfile);
    console.log(this.tradeLicenceFile);
    this.convertToBase64(this.GSTfile);
    this.convertToBase64(this.tradeLicenceFile);
    this.GSTfile = null;
    this.tradeLicenceFile = null;
  }

  convertToBase64(file: File) {
    const reader = new FileReader();
    reader.onload = () => {
      const base64String = reader.result?.toString().split(',')[1];
      console.log(base64String);
    };
    reader.readAsDataURL(file);
  }
}
