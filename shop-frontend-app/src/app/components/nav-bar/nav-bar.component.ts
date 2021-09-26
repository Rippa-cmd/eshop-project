import { Component, OnInit } from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {PRODUCT_GALLERY_URL} from "../../pages/product-gallery/product-gallery.component";
import {CART_URL} from "../../pages/cart-page/cart-page.component";

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {

  isProductGalleryPage: boolean = false;
  isCartPage: boolean = false;
  isOrderPage: boolean = false;

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.isProductGalleryPage = event.url === '/' || event.url === '/' + PRODUCT_GALLERY_URL;
        this.isCartPage = event.url === '/' + CART_URL;
        this.isOrderPage = event.url === '/';
      }
    })
  }

}
