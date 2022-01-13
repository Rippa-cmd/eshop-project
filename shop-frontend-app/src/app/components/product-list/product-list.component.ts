import {Component, Input, OnInit} from '@angular/core';
import {Product} from "../../model/product";
import {CartService} from "../../services/cart.service";
import {AddLineItemDto} from "../../model/add-line-item-dto";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {

  @Input() products?: Product[];

  constructor(private cartService: CartService) { }

  ngOnInit(): void {
  }

  addToCart(id: number) {
    this.cartService.addToCart(new AddLineItemDto(id, 1, "", ""))
      .subscribe();
  }
}
