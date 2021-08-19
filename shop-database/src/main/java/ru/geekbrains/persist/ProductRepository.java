package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.geekbrains.persist.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findProductsByNameStartsWith(String prefix);
    List<Product> findProductsByCategories(String category);

//    @Query(value = "select p from Product p where (p.name like CONCAT(:prefix, '%') or :prefix is null) " +
//            "and (p.cost >= :minCost or :minCost is null) " +
//            "and (p.cost <= :maxCost or :maxCost is null)")
//    List<Product> findAllFiltered(@Param("prefix") String prefix,
//                                  @Param("minCost") BigDecimal minCost,
//                                  @Param("maxCost") BigDecimal maxCost);
}
