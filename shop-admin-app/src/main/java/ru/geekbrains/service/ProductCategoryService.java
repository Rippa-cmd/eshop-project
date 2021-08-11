package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryService {

    Page<ProductCategory> findWithFilters(ProductCategorySearchFilters productCategorySearchFilters);

    List<ProductCategory> findAll();

    Optional<ProductCategory> findById(Long id);

    Optional<ProductCategory> findByName(String name);

    void save(ProductCategory productCategory);

    void deleteById(Long id);
}
