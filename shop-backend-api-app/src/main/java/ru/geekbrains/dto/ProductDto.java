package ru.geekbrains.dto;

import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.ProductCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    private Long id;

    private String name;

    private BigDecimal cost;

    private List<CategoryDto> categories;

    private Brand brand;

    private List<Long> pictures = new ArrayList<>();

    public ProductDto() {
    }

    public ProductDto(Long id, String name, BigDecimal cost, List<CategoryDto> categories, Brand brand) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.categories = categories;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Long> getPictures() {
        return pictures;
    }

    public void setPictures(List<Long> pictures) {
        this.pictures = pictures;
    }
}
