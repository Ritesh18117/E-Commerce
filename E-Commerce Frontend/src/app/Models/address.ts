export class Address{

    firstname:string;
    lastname:string;
    address:string;
    address2:string;
    city:string;
    state:string;
    zipcode:string;
    phone:string;
    constructor(
        firstname:string,
        lastname:string,
        address:string,
        address2:string,
        city:string,
        state:string,
        zipcode:string,
        phone:string){

        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.phone = phone;
    }
}