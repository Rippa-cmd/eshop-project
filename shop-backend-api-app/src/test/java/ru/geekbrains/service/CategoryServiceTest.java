package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.dto.CategoryDto;
import ru.geekbrains.persist.ProductCategoryRepository;
import ru.geekbrains.persist.model.ProductCategory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {

    private CategoryService categoryService;

    private ProductCategoryRepository productCategoryRepository;

    @BeforeEach
    private void init() {
        productCategoryRepository = mock(ProductCategoryRepository.class);
        categoryService = new CategoryServiceImpl(productCategoryRepository);
    }

    @Test
    public void testCategoryFindAll() {
        ProductCategory expectedCategory1 = new ProductCategory();
        expectedCategory1.setId(1L);
        expectedCategory1.setName("Category1");

        ProductCategory expectedCategory2 = new ProductCategory();
        expectedCategory2.setId(2L);
        expectedCategory2.setName("Category2");

        when(productCategoryRepository.findAll())
                .thenReturn(List.of(expectedCategory1, expectedCategory2));

        List<CategoryDto> dto = categoryService.findAll();

        assertFalse(dto.isEmpty());
        assertEquals(2, dto.size());
        assertEquals(expectedCategory1.getId(), dto.get(0).getId());
        assertEquals(expectedCategory2.getId(), dto.get(1).getId());
        assertEquals(expectedCategory1.getName(), dto.get(0).getName());
        assertEquals(expectedCategory2.getName(), dto.get(1).getName());
    }
}
