package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.persist.model.ProductCategory;
import ru.geekbrains.service.ProductCategorySearchFilters;
import ru.geekbrains.service.ProductCategoryService;
import ru.geekbrains.service.ProductService;

import javax.validation.Valid;

@Controller
@RequestMapping("/category")
public class ProductCategoryController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductCategoryService productCategoryService;

    private final ProductService productService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService, ProductService productService) {
        this.productCategoryService = productCategoryService;
        this.productService = productService;
    }

    @GetMapping
    public String listPage(Model model, ProductCategorySearchFilters productCategorySearchFilters) {
        logger.info("Product categories list page requested");

        model.addAttribute("categories", productCategoryService.findWithFilters(productCategorySearchFilters));
        return "category/categories";
    }

    @GetMapping("/new")
    public String newProductCategoryForm(Model model) {
        logger.info("New product category page requested");

        model.addAttribute("category", new ProductCategory());
        return "category/category_form";
    }

    @GetMapping("/{id}")
    public String editProductCategory(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", productCategoryService.findById(id)
                .orElseThrow(() -> new PageNotFoundException("Product category not found.")));
        return "category/category_form";
    }

    @PostMapping
    public String update(@Valid ProductCategory productCategory, BindingResult result) {
        logger.info("Saving product category");

        if (result.hasErrors())
            return "category/category_form";

        productCategoryService.save(productCategory);
        return "redirect:/category";
    }

    @GetMapping("/delete_{id}")
    public String deleteProductCategory(@PathVariable("id") Long id, Model model) {
        logger.info("Deleting product category");
        //productRepository.delete(id);
        model.addAttribute("category", productCategoryService.findById(id)
                .orElseThrow(() -> new PageNotFoundException("Product category not found.")));

        return "category/category_delete_form";
    }

    @GetMapping("/confirmed_deletion")
    public String confirmedDelete(Long id) {
        logger.info("Confirmed deleting product category - id" + id);

        productCategoryService.deleteById(id);

        return "redirect:/category";
    }

    @ExceptionHandler
    public ModelAndView pageNotFoundExceptionHandler(PageNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
