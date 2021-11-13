package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.service.dto.LineItem;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService = new CartServiceImpl();
    }

    @Test
    public void testIfNewCartIsEmpty() {
        assertNotNull(cartService.getLineItems());
        assertEquals(0, cartService.getLineItems().size());
        assertEquals(BigDecimal.ZERO, cartService.getSubTotal());
    }

    @Test
    public void testAddProduct() {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setCost(new BigDecimal(333));
        expectedProduct.setName("Product");

        cartService.addProductQty(expectedProduct, "color", "material", 2);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());

        LineItem lineItem = lineItems.get(0);
        assertEquals("color", lineItem.getColor());
        assertEquals("material", lineItem.getMaterial());
        assertEquals(2, lineItem.getQty());

        assertEquals(expectedProduct.getId(), lineItem.getProductId());
        assertNotNull(lineItem.getProductDto());
        assertEquals(expectedProduct.getName(), lineItem.getProductDto().getName());
    }

    @Test
    public void testChangeQty() {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setCost(new BigDecimal(333));
        expectedProduct.setName("Product");

        cartService.addProductQty(expectedProduct, "color", "material", 2);

        cartService.changeProductQty(expectedProduct, "color", "material", 4);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());

        LineItem lineItem = lineItems.get(0);
        assertEquals("color", lineItem.getColor());
        assertEquals("material", lineItem.getMaterial());
        assertEquals(4, lineItem.getQty());

        assertEquals(expectedProduct.getId(), lineItem.getProductId());
        assertNotNull(lineItem.getProductDto());
        assertEquals(expectedProduct.getName(), lineItem.getProductDto().getName());
    }

    @Test
    public void testDeleteProduct() {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setCost(new BigDecimal(333));
        expectedProduct.setName("Product");

        cartService.addProductQty(expectedProduct, "color", "material", 2);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());

        cartService.removeProduct(expectedProduct, "color", "material");

        lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(0, lineItems.size());

        cartService.addProductQty(expectedProduct, "color1", "material1", 2);
        cartService.addProductQty(expectedProduct, "color2", "material2", 2);

        lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(2, lineItems.size());

        cartService.removeProduct(expectedProduct, "color", "material");

        lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(2, lineItems.size());

        cartService.removeProduct(expectedProduct, "color1", "material1");

        lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());

        LineItem lineItem = lineItems.get(0);
        assertEquals("color2", lineItem.getColor());
        assertEquals("material2", lineItem.getMaterial());
        assertEquals(2, lineItem.getQty());
    }
}
