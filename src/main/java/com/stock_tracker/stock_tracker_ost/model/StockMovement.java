package com.stock_tracker.stock_tracker_ost.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private MovementType type;

    private LocalDateTime date;

    @ManyToOne
    private Product product;

    public Long getId() { return id; }
    public int getQuantity() { return quantity; }
    public MovementType getType() { return type; }
    public LocalDateTime getDate() { return date; }
    public Product getProduct() { return product; }

    public void setId(Long id) { this.id = id; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setType(MovementType type) { this.type = type; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public void setProduct(Product product) { this.product = product; }
}