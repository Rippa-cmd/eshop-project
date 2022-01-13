import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "../model/order";
import {AllCartDto} from "../model/all-cart-dto";
import {Product} from "../model/product";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) { }

  public findOrdersByUser(userId:number) {
    return this.http.get<Order[]>(`/api/v1/order/${userId}`);
  }

  public findProductsByOrder(order: Order) {
    return this.http.get<Product[]>(`/api/v1/order/products/${order.id}`);
  }

  public createOrder(order: Order) {
    this.http.post(`/api/v1/order/create`, order).subscribe();
  }
}
