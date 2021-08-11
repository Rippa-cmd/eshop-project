package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>, JpaSpecificationExecutor<ProductCategory> {
    Optional<ProductCategory> findByName(String name);

    @Modifying
    @Query(value = "delete pc, c from products_categories pc LEFT JOIN category c on c.id = pc.category_id where pc.category_id = :id", nativeQuery = true)
    void deleteById(Long id);
}
