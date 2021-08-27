package ru.geekbrains.controller;

import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.model.ProductCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    private Long id;

    private String name;

    private BigDecimal cost;

    private List<ProductCategory> categories;

    private Brand brand;

    private List<Long> pictures = new ArrayList<>();

    private MultipartFile[] newPictures;

    public ProductDto() {
    }

    public ProductDto(Long id, String name, BigDecimal cost, List<ProductCategory> categories, Brand brand) {
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

    public List<ProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ProductCategory> categories) {
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

    public MultipartFile[] getNewPictures() {
        return newPictures;
    }

    public void setNewPictures(MultipartFile[] newPictures) {
        this.newPictures = newPictures;
    }
}
