import {LineItem} from "./line-item";

export class Order {

  constructor(public id: number,
              public price: number,
              public orderDate: Date,
              public lineItems: LineItem[],
              public status: string) {
  }
}
