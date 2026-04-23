package com.stock_tracker.stock_tracker_ost.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "sale_item")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    @JsonIgnore
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private BigDecimal priceAtSale;

    public Long getId() { return id; }
    public Sale getSale() { return sale; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPriceAtSale() { return priceAtSale; }

    public void setId(Long id) { this.id = id; }
    public void setSale(Sale sale) { this.sale = sale; }
    public void setProduct(Product product) { this.product = product; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPriceAtSale(BigDecimal priceAtSale) { this.priceAtSale = priceAtSale; }
}
