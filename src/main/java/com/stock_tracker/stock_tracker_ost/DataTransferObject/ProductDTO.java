package com.stock_tracker.stock_tracker.DataTransferObject;

import java.math.BigDecimal;

public class ProductDTO {

    private Long id;
    private String name;
    private int quantity;
    private BigDecimal price;

    public ProductDTO(Long id, String name, int quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
}
