package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.dto.AddLineItemDto;
import ru.geekbrains.dto.AllCartDto;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.service.CartService;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.dto.LineItem;

import java.util.List;

@RequestMapping("/cart")
@RestController
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/all")
    public AllCartDto findAll() {
        return new AllCartDto(cartService.getLineItems(), cartService.getSubTotal());
    }

    @PostMapping(path="/add", produces = "application/json", consumes = "application/json")
    public List<LineItem> addToCart(@RequestBody AddLineItemDto addLineItemDto){
        logger.info("New LineItem. ProductId = {}, qty = {}", addLineItemDto.getProductId(), addLineItemDto.getQty());

        ProductDto productDto = productService.findById(addLineItemDto.getProductId())
                .orElseThrow(RuntimeException::new);

        cartService.addProductQty(productDto, addLineItemDto.getColor(), addLineItemDto.getMaterial(), addLineItemDto.getQty());

        return cartService.getLineItems();
    }

    @PostMapping(path="/changeQty", produces = "application/json", consumes = "application/json")
    public void changeQty(@RequestBody AddLineItemDto addLineItemDto) {
        logger.info("Changing LineItem qty. ProductId = {}, qty = {}", addLineItemDto.getProductId(), addLineItemDto.getQty());
        ProductDto productDto = productService.findById(addLineItemDto.getProductId())
                .orElseThrow(RuntimeException::new);

        cartService.changeProductQty(productDto, addLineItemDto.getColor(), addLineItemDto.getMaterial(), addLineItemDto.getQty());
    }

    @PostMapping(path ="/delete", produces = "application/json", consumes = "application/json")
    public void removeProduct(@RequestBody AddLineItemDto addLineItemDto) {
        logger.info("Delete LineItem. ProductId = {}, qty = {}", addLineItemDto.getProductId(), addLineItemDto.getQty());

        ProductDto productDto = productService.findById(addLineItemDto.getProductId())
                .orElseThrow(RuntimeException::new);

        cartService.removeProduct(productDto, addLineItemDto.getColor(), addLineItemDto.getMaterial());
    }

    @DeleteMapping(path = "/deleteAll")
    public void deleteAll() {
        logger.info("Delete all products");

        cartService.clearCart();
    }
}
