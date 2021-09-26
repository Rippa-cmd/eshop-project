package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.dto.ProductDto;

import java.util.List;
import java.util.Optional;


public interface ProductService {

    Page<ProductDto> findWithFilters(ProductSearchFilters productSearchFilters);

    Optional<ProductDto> findById(Long id);
}
