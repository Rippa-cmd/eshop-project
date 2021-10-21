import {Component, OnInit} from '@angular/core';
import {OrderService} from "../../services/order.service";
import {Order} from "../../model/order";
import {ProductService} from "../../services/product.service";
import {LineItem} from "../../model/line-item";
import {OrderStatusService} from "../../services/order-status.service";

export const ORDERS_URL = 'order';

@Component({
  selector: 'app-order-page',
  templateUrl: './order-page.component.html',
  styleUrls: ['./order-page.component.scss']
})
export class OrderPageComponent implements OnInit {

  orders: Order[] = [];

  products?: LineItem[];

  viewProducts: boolean = false;

  constructor(private orderService: OrderService,
              private productService: ProductService,
              private orderStatusService: OrderStatusService) {
  }

  ngOnInit(): void {
    this.orderService.findOrdersByUser(1)
      .subscribe(orders => {
          this.orders = orders;
        },
        error => {
          console.log(error);
        });
    this.orderStatusService.onMessage('/order_out/order')
      .subscribe(msg => console.log(`New message with status ${msg.state}`))
  }

  public orderedProducts(order: Order) {
    this.products = order.lineItems;
    this.viewProducts = true;
  } // todo список и кол-во продуктов
}
