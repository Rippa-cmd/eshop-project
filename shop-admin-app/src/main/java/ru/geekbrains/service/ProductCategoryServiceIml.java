package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.*;
import ru.geekbrains.persist.model.ProductCategory;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceIml implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryServiceIml(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public Page<ProductCategory> findWithFilters(ProductCategorySearchFilters productCategorySearchFilters) {
        Specification<ProductCategory> spec = Specification.where(null);
        Sort sortConfiguration;
        if (productCategorySearchFilters.getProductCategoryNameFilter() != null && !productCategorySearchFilters.getProductCategoryNameFilter().isBlank())
                spec = spec.and(ProductCategorySpecifications.productCategoryNamePrefix(productCategorySearchFilters.getProductCategoryNameFilter()));

        if (productCategorySearchFilters.getSize() == null || productCategorySearchFilters.getSize() <= 0)
            productCategorySearchFilters.setSize(5);

        if (productCategorySearchFilters.getPage() == null || productCategorySearchFilters.getPage() <= 0)
            productCategorySearchFilters.setPage(1);

        if (productCategorySearchFilters.getSortField() != null && !productCategorySearchFilters.getSortField().isBlank())
            sortConfiguration = Sort.by(productCategorySearchFilters.getSortField());
        else
            sortConfiguration = Sort.by("id");

        if ("desc".equals(productCategorySearchFilters.getSortDirection()))
            sortConfiguration = sortConfiguration.descending();

        return productCategoryRepository.findAll(spec, PageRequest.of(productCategorySearchFilters.getPage() - 1,
                productCategorySearchFilters.getSize(), sortConfiguration));
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public Optional<ProductCategory> findById(Long id) {
        return productCategoryRepository.findById(id);
    }

    @Override
    public Optional<ProductCategory> findByName(String name) {
        return productCategoryRepository.findByName(name);
    }

    @Override
    public void save(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }

    @Override
    public void deleteById(Long id) {
        productCategoryRepository.deleteById(id);
    }
}
