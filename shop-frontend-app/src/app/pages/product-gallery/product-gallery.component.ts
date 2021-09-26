import { Component, OnInit } from '@angular/core';
import {ProductService} from "../../services/product.service";
import {Product} from "../../model/product";
import {Page} from "../../model/page";
import {ProductFilterDto} from "../../model/product-filter";

export const PRODUCT_GALLERY_URL = 'product';

@Component({
  selector: 'app-product-gallery',
  templateUrl: './product-gallery.component.html',
  styleUrls: ['./product-gallery.component.scss']
})
export class ProductGalleryComponent implements OnInit {

  products: Product[] = [];

  page?: Page;

  isError: boolean = false;

  productFilter?: ProductFilterDto;

  pageNumber: number = 1;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.productService.findAll()
      .subscribe(
        res => {
          console.log("Loading products");
          this.page = res;
          this.products = res.content;
        },
        err => {
          console.log(`Can't load products ${err}`);
        });
  }

  filterApplied($event: ProductFilterDto) {
    console.log($event);
    if ($event.minPrice < 0)
      $event.minPrice = 0;
    if ($event.maxPrice < 0 || $event.maxPrice < $event.minPrice)
      $event.maxPrice = 0;
    this.productFilter = $event;
    this.productService.findAll($event, 1)
      .subscribe(
        res => {
          this.page = res;
          this.products = res.content;
          this.pageNumber = 1;
        },
        err => {
          console.log(`Can't load products ${err}`);
        });
  }

  goToPage($event: number) {
    this.productService.findAll(this.productFilter, $event)
      .subscribe(
        res => {
          this.page = res;
          this.products = res.content;
          this.pageNumber = res.number + 1;
        },
        err => {
          console.log(`Can't load products ${err}`);
        });
  }

}
