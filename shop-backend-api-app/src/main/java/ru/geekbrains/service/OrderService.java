package ru.geekbrains.service;

import ru.geekbrains.dto.OrderDto;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.persist.model.Product;

import java.util.List;

public interface OrderService {

    List<OrderDto> findByUserId(Long userId);

//    List<ProductDto> findProductsByOrder(Long orderId);

    void createOrder(OrderDto orderDto);
}
