import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {CartService} from "../../services/cart.service";
import {AllCartDto} from "../../model/all-cart-dto";
import {LineItem} from "../../model/line-item";
import {AddLineItemDto} from "../../model/add-line-item-dto";

export const CART_URL = 'cart'

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.scss']
})
export class CartPageComponent implements OnInit{

  content?: AllCartDto;

  constructor(private cartService: CartService) { }

  ngOnInit(): void {
    this.cartService.findAll()
      .subscribe(res => {
        this.content = res;
      });
  }


  removeProduct(lineItem: LineItem) {
    this.cartService.deleteLineItem(lineItem).subscribe(res => {
      this.content = res;
    },
      err => {
        console.log(`Can't load products ${err}`);
      });
  }

  changeQty(lineItem: LineItem) {
    this.cartService.changeQty(new AddLineItemDto(lineItem.productId, lineItem.qty, lineItem.color, lineItem.material));
  }

  removeAllProducts() {
    this.cartService.deleteAll();
  }
}
