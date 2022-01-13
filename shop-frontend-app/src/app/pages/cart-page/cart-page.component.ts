import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {CartService} from "../../services/cart.service";
import {AllCartDto} from "../../model/all-cart-dto";
import {LineItem} from "../../model/line-item";
import {AddLineItemDto} from "../../model/add-line-item-dto";
import {OrderService} from "../../services/order.service";
import {Order} from "../../model/order";
import {AuthGuard} from "../../helpers/auth-guard";

export const CART_URL = 'cart'

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.scss']
})
export class CartPageComponent implements OnInit {

  content?: AllCartDto;

  isEmptyOrder: boolean = false;

  constructor(private cartService: CartService,
              private orderService: OrderService,
              private authGuard: AuthGuard) {
  }

  ngOnInit(): void {
    this.cartUpdated();
  }

  cartUpdated() {
    this.cartService.findAll().subscribe(
      res => {
        this.content = res;
      }
    )
  }
  //
  //
  // removeProduct(lineItem: LineItem) {
  //   this.cartService.deleteLineItem(lineItem).subscribe(res => {
  //       this.content = res;
  //     },
  //     err => {
  //       console.log(`Can't load products ${err}`);
  //     });
  // }

  // changeQty(lineItem: LineItem) {
  //   this.cartService.changeQty(new AddLineItemDto(lineItem.productId, lineItem.qty, lineItem.color, lineItem.material));
  // }

  removeAllProducts() {
    this.cartService.deleteAll().subscribe(res => {
      this.content = res;
    });
  }

  createOrder() {
    if (this.authGuard.checkLogin("/cart")) {
      if (this.content) {
        this.orderService.createOrder(new Order(1, this.content.subtotal, new Date, this.content.lineItems, ""));
      } else {
        this.isEmptyOrder = true;
      }
    }
  }
}
