package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.dto.OrderDto;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.service.CartService;
import ru.geekbrains.service.OrderService;

import java.util.List;

@RequestMapping("/order")
@RestController
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    private final CartService cartService;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public List<OrderDto> findByUserId(@PathVariable("userId") Long userId) {
        return orderService.findByUserId(userId);
    }

//    @GetMapping("/product/{orderId}")
//    public List<ProductDto> findProductsByOrder(@PathVariable("orderId") Long orderId) {
//        return orderService.findProductsByOrder(orderId);
//    } todo список и кол-во продуктов

    @PostMapping(path="/create", produces = "application/json", consumes = "application/json")
    public void createOrder(@RequestBody OrderDto orderDto) {
        orderService.createOrder(orderDto);
        cartService.clearCart();
    }
}
