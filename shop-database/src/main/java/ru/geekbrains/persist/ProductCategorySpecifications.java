package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;

public final class ProductCategorySpecifications {

    public static Specification<ProductCategory> productCategoryNamePrefix(String prefix) {
        return (root, query, builder) -> builder.like(root.get("name"), prefix + "%");
    }
}
