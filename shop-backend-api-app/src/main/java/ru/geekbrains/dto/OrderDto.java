package ru.geekbrains.dto;

import ru.geekbrains.service.dto.LineItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDto {

    private Long id;

    private Long userId;

    private Date orderDate;

    private BigDecimal price;

    private String status;

    private List<LineItem> lineItems;

    public OrderDto() {
    }

    public OrderDto(Long id, Long userId, Date orderDate, BigDecimal price, String status, List<LineItem> lineItems) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.price = price;
        this.status = status;
        this.lineItems = lineItems;
    }

    public OrderDto(Long id, Date orderDate, BigDecimal price, String status) {
        this.id = id;
        this.orderDate = orderDate;
        this.price = price;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
