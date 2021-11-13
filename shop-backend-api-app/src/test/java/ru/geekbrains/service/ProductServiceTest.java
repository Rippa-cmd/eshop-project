package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.model.ProductCategory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ProductServiceTest {

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    private void init() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    public void testFindById() {
        ProductCategory expectedCategory = new ProductCategory();
        expectedCategory.setId(1L);
        expectedCategory.setName("Category");

        Picture expectedPicture = new Picture();
        expectedPicture.setId(1L);
        expectedPicture.setName("Picture");
        expectedPicture.setStorageUUID("0dcbf48b-3406-4a67-b367-64bc11411b07");

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Product");
        expectedProduct.setCategories(List.of(expectedCategory));
        expectedProduct.setPictures(List.of(expectedPicture));
        expectedProduct.setCost(new BigDecimal(123));

        when(productRepository.findById(eq(expectedProduct.getId())))
                .thenReturn(Optional.of(expectedProduct));

        Optional<ProductDto> opt = productService.findById(expectedProduct.getId());

        assertTrue(opt.isPresent());
        assertEquals(expectedProduct.getId(), opt.get().getId());
        assertEquals(expectedProduct.getName(), opt.get().getName());
        assertEquals(expectedProduct.getCategories().get(0).getId(), opt.get().getCategories().get(0).getId());
        assertEquals(expectedProduct.getCategories().get(0).getName(), opt.get().getCategories().get(0).getName());
        assertEquals(expectedProduct.getPictures().get(0).getId(), opt.get().getPictures().get(0));
        assertEquals(expectedProduct.getCost(), opt.get().getCost());
    }
}
