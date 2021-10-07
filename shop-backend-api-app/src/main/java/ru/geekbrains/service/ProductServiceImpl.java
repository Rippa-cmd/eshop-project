package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.CategoryDto;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecifications;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.model.Product;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDto> findAll(Optional<Long> categoryId, Optional<String> namePattern,
                                    Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice,
                                    Optional<String> category, Integer page, Integer size, String sortField) {
        Specification<Product> spec = Specification.where(null);
//        if (categoryId.isPresent() && categoryId.get() != -1) {
//            spec = spec.and(ProductSpecifications.productCategoryPrefix(categoryId.get()));
//        }
        if (namePattern.isPresent()) {
            spec = spec.and(ProductSpecifications.productNamePrefix(namePattern.get()));
        }
        if (minPrice.isPresent()) {
            spec = spec.and(ProductSpecifications.minCost(minPrice.get()));
        }
        if (maxPrice.isPresent()) {
            if (maxPrice.get().compareTo(BigDecimal.ZERO) > 0)
                spec = spec.and(ProductSpecifications.maxCost(maxPrice.get()));
        }
        if (category.isPresent() && !category.get().isBlank()) {
            spec = spec.and(ProductSpecifications.productCategoryPrefix(category.get()));
        }
        return productRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortField)))
                .map(product -> {
                    ProductDto productDto = new ProductDto(product.getId(),
                            product.getName(),
                            product.getCost(),
                            product.getCategories().stream()
                                    .map(productCategory -> new CategoryDto(productCategory.getId(), productCategory.getName()))
                                    .collect(Collectors.toList()),
                            product.getBrand());
                    if (product.getPictures().size() != 0)
                        productDto.setPictures(product.getPictures().stream()
                                .map(Picture::getId)
                                .collect(Collectors.toList())
                        );
                    return productDto;
                });

    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    ProductDto productDto = new ProductDto(product.getId(),
                            product.getName(),
                            product.getCost(),
                            product.getCategories().stream()
                                    .map(productCategory -> new CategoryDto(productCategory.getId(), productCategory.getName()))
                                    .collect(Collectors.toList()),
                            product.getBrand()
                    );
                    if (product.getPictures().size() != 0)
                        productDto.setPictures(product.getPictures().stream()
                                .map(Picture::getId)
                                .collect(Collectors.toList())
                        );
                    return productDto;
                });
    }
}
