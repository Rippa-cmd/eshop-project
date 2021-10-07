package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.OrderDto;
import ru.geekbrains.persist.ProductOrderRepository;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.model.ProductOrder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    private final ProductOrderRepository productOrderRepository;

    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(ProductOrderRepository productOrderRepository, ProductRepository productRepository) {
        this.productOrderRepository = productOrderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderDto> findByUserId(Long userId) {
        return productOrderRepository.findByUserId(userId).stream()
                .map(order -> new OrderDto(order.getId(), order.getDate(), order.getPrice(), order.getStatus()))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<ProductDto> findProductsByOrder(Long orderId) {
//        return productOrderRepository.findProductsByProductOrder(orderId).stream()
//                .map(product -> new ProductDto(product.getId(), product.getName(),
//                        product.getCost(), product.getCategories().stream()
//                        .map(productCategory -> new CategoryDto(productCategory.getId(), productCategory.getName()))
//                        .collect(Collectors.toList()), product.getBrand())).collect(Collectors.toList());
//    }

    @Override
    public void createOrder(OrderDto orderDto) {
        ProductOrder order = new ProductOrder(1L, orderDto.getPrice(),
                orderDto.getLineItems().stream().map(lineItem -> productRepository.findById(lineItem.getProductId()).get())
        .collect(Collectors.toList()));

        productOrderRepository.save(order);
    }
}
