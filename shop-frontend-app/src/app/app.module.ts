import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { FooterComponent } from './components/footer/footer.component';
import { ProductGalleryComponent } from './pages/product-gallery/product-gallery.component';
import { HttpClientModule } from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {PaginationComponent} from "./components/pagination/pagination.component";
import { ProductFilterComponent } from './components/product-filter/product-filter.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    FooterComponent,
    ProductGalleryComponent,
    PaginationComponent,
    ProductFilterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
