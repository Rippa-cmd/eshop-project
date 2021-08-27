package ru.geekbrains.controller;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.persist.BrandRepository;
import ru.geekbrains.service.ProductCategoryService;
import ru.geekbrains.service.ProductSearchFilters;
import ru.geekbrains.service.ProductService;

import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    private final ProductCategoryService productCategoryService;

    private final BrandRepository brandRepository;

    @Autowired
    private ProductController(ProductService productService, ProductCategoryService productCategoryService, BrandRepository brandRepository) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.brandRepository = brandRepository;
    }

    @GetMapping
    public String listPage(Model model, ProductSearchFilters productSearchFilters) {
        logger.info("Product list page requested");

        model.addAttribute("products", productService.findWithFilters(productSearchFilters));
        return "product/products";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        logger.info("New product page requested");

        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", productCategoryService.findAll());
        model.addAttribute("brands", brandRepository.findAll());

        return "product/product_form";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        logger.info("Product editing page requested");

        model.addAttribute("product", productService.findById(id)
                .orElseThrow(() -> new PageNotFoundException("Product not found.")));
        model.addAttribute("categories", productCategoryService.findAll());
        model.addAttribute("brands", brandRepository.findAll());

        return "product/product_form";
    }

    @PostMapping
    public String update(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult result) throws NotFoundException {
        logger.info("Saving product");

        if (result.hasErrors())
            return "product/product_form";

        productService.save(productDto);
        return "redirect:/product";
    }

    @GetMapping("/delete_{id}")
    public String deleteProduct(@PathVariable("id") Long id, Model model) {
        logger.info("Deleting product");
        //productRepository.delete(id);
        model.addAttribute("product", productService.findById(id)
                .orElseThrow(() -> new PageNotFoundException("Product not found.")));

        return "product/delete_form";
    }

    @GetMapping("/confirmed_deletion")
    public String confirmedDelete(Long id) {
        logger.info("Confirmed deleting product - id" + id);

        productService.deleteById(id);

        return "redirect:/product";
    }

    @ExceptionHandler
    public ModelAndView pageNotFoundExceptionHandler(PageNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

}
