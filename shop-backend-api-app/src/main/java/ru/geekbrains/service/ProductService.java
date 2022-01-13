package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface ProductService {

    Page<ProductDto> findAll(Optional<Long> categoryId, Optional<String> namePattern,
                             Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice,
                             Optional<String> category, Integer page, Integer size, String sortField);

    Optional<ProductDto> findById(Long id);
}
