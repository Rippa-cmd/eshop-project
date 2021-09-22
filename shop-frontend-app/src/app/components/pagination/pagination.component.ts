import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Page} from "../../model/page";

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss']
})
export class PaginationComponent implements OnInit {

  @Input() page? : Page;

  numbers: number[] = [];

  pageNumber: number = 1;

  @Output() goToPageEvent = new EventEmitter<number>();

  constructor() { }

  ngOnInit(): void {
    console.log(this.page?.totalPages);
    this.numbers = Array.from(Array(this.page?.totalPages).keys());

  }

  goToPage(page: number) {
    console.log(`Page number ${page}`)
    this.goToPageEvent.emit(page);
  }
}
