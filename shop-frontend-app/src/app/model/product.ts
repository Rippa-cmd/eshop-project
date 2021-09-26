import {Category} from "./category";

export class Product {

  constructor(public id: number,
              public name: string,
              public cost: number,
              public category: Category[],
              public pictures: number[]) {
  }
}
