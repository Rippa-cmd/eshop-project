import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PRODUCT_GALLERY_URL, ProductGalleryComponent} from "./pages/product-gallery/product-gallery.component";
import {CART_URL, CartPageComponent} from "./pages/cart-page/cart-page.component";
import {OrderPageComponent, ORDERS_URL} from "./pages/order-page/order-page.component";
import {AuthGuard} from "./helpers/auth-guard";
import {LOGIN_URL, LoginPageComponent} from "./pages/login-page/login-page.component";

const routes: Routes = [
  {path: "", pathMatch: "full", redirectTo: PRODUCT_GALLERY_URL},
  {path: PRODUCT_GALLERY_URL, component: ProductGalleryComponent},
  {path: LOGIN_URL, component: LoginPageComponent},
  {path: CART_URL, component: CartPageComponent},
  {path: ORDERS_URL, component: OrderPageComponent, canActivate: [AuthGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
