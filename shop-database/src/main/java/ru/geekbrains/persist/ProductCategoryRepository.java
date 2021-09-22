package ru.geekbrains.persist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.model.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>, JpaSpecificationExecutor<ProductCategory> {


    Optional<ProductCategory> findByName(String name);

    @Modifying
    @Query(value = "delete pc, c from products_categories pc LEFT JOIN category c on c.id = pc.category_id where pc.category_id = :id", nativeQuery = true)
    void deleteById(Long id);
}
