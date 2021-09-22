package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.model.Product;

import javax.persistence.criteria.JoinType;
import java.math.BigDecimal;

public final class ProductSpecifications {

    public static Specification<Product> productNamePrefix(String prefix) {
        return (root, query, builder) -> builder.like(root.get("name"), prefix + "%");
    }

    public static Specification<Product> productCategoryPrefix(String prefix) {
        return (root, query, builder) ->
                builder.equal(
                        root.join("categories").get("name"), prefix);
    }

    public static Specification<Product> emptyProductCategoryPrefix() {
        return (root, query, builder) ->
                builder.isNull(
                        root.join("categories", JoinType.LEFT).get("name"));
    }

    public static Specification<Product> minCost(BigDecimal minCost) {
        return (root, query, builder) -> builder.ge(root.get("cost"), minCost);
    }

    public static Specification<Product> maxCost(BigDecimal maxCost) {
        return (root, query, builder) -> builder.le(root.get("cost"), maxCost);
    }
}
