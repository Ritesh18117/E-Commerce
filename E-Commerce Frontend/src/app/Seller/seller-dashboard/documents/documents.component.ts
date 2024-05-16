import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { SellerService } from 'src/app/Services/seller.service';

@Component({
  selector: 'app-documents',
  templateUrl: './documents.component.html',
  styleUrls: ['./documents.component.css']
})
export class DocumentsComponent {
  confirmation: boolean = false;
  uploadDocs: boolean = false;
  showUpload : boolean = true;
  GSTfile:any;
  tradeLicenceFile:any;
  gstImage:any;
  licenceImage:any;
  seller:any;
  token:any;

  constructor(private toastr: ToastrService,private sellerService:SellerService){}

  ngOnInit(){
    this.token = sessionStorage.getItem('token');
    this.getMyDocuments();
  }

  getMyDocuments(){
    this.sellerService.getMyDocuments(this.token).subscribe(
      (data) =>{
        console.log(data);
        this.seller = data;
      },(error) =>{
        console.error(error);
      }
    )
  }

  onFileSelected(event: any, index: number) {
    if (index === 1) {
      this.GSTfile = event.target.files[0];
      console.log(this.GSTfile);
    }
    if (index === 2) {
      this.tradeLicenceFile = event.target.files[0];
      console.log(this.tradeLicenceFile);
    }
  }

  onSubmit() {
    console.log(this.GSTfile);
    console.log(this.tradeLicenceFile);

    const formData: FormData = new FormData();
    formData.append('gstDocument', this.GSTfile);
    formData.append('licenceDocument', this.tradeLicenceFile);

    if (this.GSTfile && this.tradeLicenceFile) {
      this.sellerService.uploadDocuments(this.token,formData)
        .subscribe(
          (response) => {
            console.log('Documents uploaded successfully:', response);
            this.toastr.success('Document Uploaded!!', 'Success', {
              timeOut: 500, // Toast will disappear after 0.5 seconds
            });
            this.GSTfile = "";
            this.tradeLicenceFile = "";
          },
          (error) => {
            this.toastr.error('Document Uploading Error!!', 'Error', {
              timeOut: 500, // Toast will disappear after 0.5 seconds
            });
            console.error('Error uploading documents:', error);
          }
        );
    } else {
      console.error('Both GST document and trade license file are required.');
    }
  }



  convertToImage(image:any) {
    const blob = this.base64toBlob(image, 'image/png'); // Change 'image/png' to the appropriate content type
    const urlCreator = window.URL || window.webkitURL;
    return urlCreator.createObjectURL(blob);
  }

  private base64toBlob(base64Data: string, contentType: string) {
    const byteCharacters = atob(base64Data);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    return new Blob([byteArray], { type: contentType });
  }

  toggleUpload() {
    this.uploadDocs = !this.uploadDocs // Toggle the visibility of the form
  }
  toggleShowUpload(){
    this.showUpload = !this.showUpload
  }
  toggleConfirmation(){
    this.confirmation = !this.confirmation;
  }
}
