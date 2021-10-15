package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.persist.model.ProductOrder;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    List<ProductOrder> findByUserId(Long userId);


//    List<Product> findProductsByProductOrder(Long productId);
}
