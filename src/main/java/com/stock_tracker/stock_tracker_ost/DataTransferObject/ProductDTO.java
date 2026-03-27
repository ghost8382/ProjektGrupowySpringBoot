package com.stock_tracker.stock_tracker_ost.DataTransferObject;

import java.math.BigDecimal;

public class ProductDTO {

    private Long id;
    private String name;
    private int quantity;
    private BigDecimal price;
    private String categoryName;

    public ProductDTO(Long id, String name, int quantity, BigDecimal price, String categoryName) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.categoryName = categoryName;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
    public String getCategoryName() { return categoryName; }
}