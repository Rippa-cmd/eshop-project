package ru.geekbrains.service;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import ru.geekbrains.controller.ProductDto;
import ru.geekbrains.persist.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Page<ProductDto> findWithFilters(ProductSearchFilters productSearchFilters);

    Optional<ProductDto> findById(Long id);

    void save(ProductDto productDto) throws NotFoundException;

    void deleteById(Long id);

    List<ProductDto> findAll();
}
