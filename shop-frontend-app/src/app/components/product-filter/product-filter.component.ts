import {Component, OnInit, Output, EventEmitter} from '@angular/core';
import {ProductFilterDto} from "../../model/product-filter";
import {Category} from "../../model/category";
import {CategoryService} from "../../services/category.service";

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrls: ['./product-filter.component.scss']
})
export class ProductFilterComponent implements OnInit {

  @Output() filterApplied = new EventEmitter<ProductFilterDto>();

  categories?: Category[];

  selectedCat?: Category;

  productFilter: ProductFilterDto = new ProductFilterDto("", 0, 0, "");

  constructor(private categoryService: CategoryService) {
  }

  ngOnInit(): void {
    this.categoryService.findAll()
      .subscribe(res => {
        this.categories = res;
      });
  }

  applyFilter() {
    this.filterApplied.emit(this.productFilter);
  }

  selectCategory(category: Category) {
    if (this.selectedCat == category) {
      this.selectedCat = new Category(1, "");
      this.productFilter.category = "";
    } else {
      this.selectedCat = category;
      this.productFilter.category = category.name;
    }
    this.filterApplied.emit(this.productFilter);
  }
}
