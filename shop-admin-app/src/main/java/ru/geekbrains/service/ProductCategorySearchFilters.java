package ru.geekbrains.service;

public class ProductCategorySearchFilters {
    private String productCategoryNameFilter;
    private Integer page;
    private Integer size;
    private String sortField;
    private String sortDirection;

    public String getProductCategoryNameFilter() {
        return productCategoryNameFilter;
    }

    public void setProductCategoryNameFilter(String productCategoryNameFilter) {
        this.productCategoryNameFilter = productCategoryNameFilter;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
