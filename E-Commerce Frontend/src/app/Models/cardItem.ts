import { Product } from "./Product";

export class cardItem{
    id:number;
    product:Product;
    color:string;
    size:number;
    quantity:number;
    constructor(id:number,product:Product,color:string,size:number,quantity:number){
        this.id = id;
        this.product = product;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }
}