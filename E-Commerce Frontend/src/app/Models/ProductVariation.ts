import { Color } from "./Color";
import { Product } from "./Product";
import { Size } from "./Size";

export class ProductVariation{
    id:number
    product:Product;
    size:Size;
    color:Color;
    quantity:number;

    constructor(id:number,product:Product,size:Size,color:Color,quantity:number){
        this.id = id;
        this.product = product;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }
}