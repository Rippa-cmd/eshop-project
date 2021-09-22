package ru.geekbrains.persist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.geekbrains.persist.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @EntityGraph(attributePaths = {"categories", "brand"})
//    @Query(value = "select p from Product p left join fetch p.categories",
//            countQuery = "select count(p) from Product p")
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"categories", "brand"})
    Optional<Product> findById(Long id);

    List<Product> findProductsByNameStartsWith(String prefix);
    List<Product> findProductsByCategories(String category);

//    @Query(value = "select p from Product p where (p.name like CONCAT(:prefix, '%') or :prefix is null) " +
//            "and (p.cost >= :minCost or :minCost is null) " +
//            "and (p.cost <= :maxCost or :maxCost is null)")
//    List<Product> findAllFiltered(@Param("prefix") String prefix,
//                                  @Param("minCost") BigDecimal minCost,
//                                  @Param("maxCost") BigDecimal maxCost);
}
