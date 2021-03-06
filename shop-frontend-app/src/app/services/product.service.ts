import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Page} from "../model/page";
import {ProductFilterDto} from "../model/product-filter";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  public findAll(productFilter?: ProductFilterDto, page? : number) : Observable<Page> {
    let params = new HttpParams();
    if (productFilter?.namePattern != null) {
      params = params.set('namePattern', productFilter?.namePattern);
    }
    if (productFilter?.minPrice != null) {
      params = params.set('minPrice', productFilter?.minPrice);
    }
    if (productFilter?.maxPrice != null) {
      params = params.set('maxPrice', productFilter?.maxPrice);
    }
    if (productFilter?.category != null) {
      params = params.set('category', productFilter?.category);
    }

    params = params.set("page", page != null ? page : 1);
    params = params.set("size", 6);
    return this.http.get<Page>('/api/v1/product/all', { params: params });
  }
}
