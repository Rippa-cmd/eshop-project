package ru.geekbrains.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
import ru.geekbrains.persist.ProductCategoryRepository;
import ru.geekbrains.persist.model.ProductCategory;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private CategoryControllerTest categoryController;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private SimpMessagingTemplate template;

    @Test
    public void testCategoriesDetails() throws Exception {
        ProductCategory productCategory1 = productCategoryRepository.save(new ProductCategory(1L, "Category1"));
        ProductCategory productCategory2 = productCategoryRepository.save(new ProductCategory(2L, "Category2"));

        mvc.perform(MockMvcRequestBuilders
                        .get("/category/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(productCategory1.getName())))
                .andExpect(jsonPath("$[1].name", is(productCategory2.getName())));
                //.andExpect(jsonPath("$.content[0].cost", is(product.getCost().setScale(1)))) todo
    }
}
