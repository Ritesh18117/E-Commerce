import { Category } from "./Category";
import { Seller } from "./Seller";


export class Product{
  id: number;
  seller: Seller;
  category: Category;
  name: string;
  price: number;
  discount: number;
  margin: number;
  gender: string;
  description: string;
  imageURL: string;
  approvalStatus: string;

  constructor(
    id: number,
    seller: Seller,
    category: Category,
    name: string,
    price: number,
    discount: number,
    margin: number,
    gender: string,
    description: string,
    imageURL: string,
    approvalStatus: string
  ) {
    this.id = id;
    this.seller = seller;
    this.category = category;
    this.name = name;
    this.price = price;
    this.discount = discount;
    this.margin = margin;
    this.gender = gender;
    this.description = description;
    this.imageURL = imageURL;
    this.approvalStatus = approvalStatus;
  }
}



// export class Product {
//     id: number;
//     name: string;
//     description: string;
//     brand: string;
//     gender: string;
//     category: string;
//     size: number[];
//     color: any[];
//     price: number;
//     discountPrice?: number;
//     is_in_inventory: boolean;
//     items_left: number;
//     imageURL: string;
//     slug: string;

//     constructor(
//         id: number, name: string, description: string, brand: string, gender: string, category: string, size: number[], color: string[], price: number,
//         is_in_inventory: boolean, items_left: number, imageURL: string, slug: string, discountPrice?: number) {
//         this.id = id;
//         this.name = name;
//         this.description = description;
//         this.brand = brand;
//         this.gender = gender;
//         this.category = category;
//         this.size = size;
//         this.color = color;
//         this.price = price;
//         this.discountPrice = discountPrice;
//         this.is_in_inventory = is_in_inventory;
//         this.items_left = items_left;
//         this.imageURL = imageURL;
//         this.slug = slug;
//     }
// }