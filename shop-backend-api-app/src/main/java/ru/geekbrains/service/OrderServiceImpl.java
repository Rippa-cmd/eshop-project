package ru.geekbrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.OrderDto;
import ru.geekbrains.persist.ProductOrderRepository;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.model.ProductOrder;
import ru.geekbrains.service.dto.OrderMessage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final ProductOrderRepository productOrderRepository;

    private final ProductRepository productRepository;

    private final RabbitTemplate rabbitTemplate;

    private final SimpMessagingTemplate template;

    @Autowired
    public OrderServiceImpl(ProductOrderRepository productOrderRepository, ProductRepository productRepository,
                            RabbitTemplate rabbitTemplate, SimpMessagingTemplate template) {
        this.productOrderRepository = productOrderRepository;
        this.productRepository = productRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.template = template;
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
        rabbitTemplate.convertAndSend("order.exchange", "new_order", new OrderMessage(order.getId(), order.getStatus()));
    }

    @RabbitListener(queues = "processed.order.queue")
    public void receive(OrderMessage order) {
        logger.info("Order with id '{}' state change to '{}'", order.getId(), order.getState());
        ProductOrder productOrder = productOrderRepository.findById(order.getId()).get();
        productOrder.setStatus(order.getState());
        productOrderRepository.save(productOrder);
        template.convertAndSend("/order_out/order", order);
    }
}
