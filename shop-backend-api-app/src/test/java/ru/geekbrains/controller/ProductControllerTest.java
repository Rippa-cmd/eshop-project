package ru.geekbrains.controller;


import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.geekbrains.persist.BrandRepository;
import ru.geekbrains.persist.PictureRepository;
import ru.geekbrains.persist.ProductCategoryRepository;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.model.ProductCategory;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private SimpMessagingTemplate template;

    @Test
    public void testProductDetails() throws Exception {
        ProductCategory category = productCategoryRepository.save(new ProductCategory(null,"Category"));
        Brand brand = brandRepository.save(new Brand(null, "Brand"));
        Product product = productRepository.save(new Product(null, "Product", new BigDecimal(1234), List.of(category), brand));

        Picture picture1 = pictureRepository.save(new Picture(1L, "Picture1", "image/png", "0dcbf48b-3406-4a67-b367-64bc11411b07", product));
        Picture picture2 = pictureRepository.save(new Picture(2L, "Picture2", "image/png", "7fd88d6f-f1dd-433d-a2ca-1ed088e75587", product));

        product.setPictures(List.of(picture1, picture2));
        productRepository.save(product);

        mvc.perform(MockMvcRequestBuilders
                        .get("/product/all")
                        .param("categoryId", category.getId().toString())
                        .param("page", "1")
                        .param("size", "5")
                        .param("sortField", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is(product.getName())))
                //.andExpect(jsonPath("$.content[0].cost", is(product.getCost().setScale(1)))) todo
                .andExpect(jsonPath("$.content[0].categories[0].name", is(category.getName())))
                .andExpect(jsonPath("$.content[0].pictures[0]", is(product.getPictures().get(0).getId().intValue())))
                .andExpect(jsonPath("$.content[0].pictures[1]", is(product.getPictures().get(1).getId().intValue())));
    }

}
