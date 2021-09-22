package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.geekbrains.persist.model.Brand;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {
    Optional<Brand> findByName(String name);

//    @Modifying
//    @Query(value = "delete pc, c from products_categories pc LEFT JOIN category c on c.id = pc.category_id where pc.category_id = :id", nativeQuery = true)
//    void deleteById(Long id);
}
