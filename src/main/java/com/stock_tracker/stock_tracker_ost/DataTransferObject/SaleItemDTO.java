package com.stock_tracker.stock_tracker_ost.DataTransferObject;

import java.math.BigDecimal;

public class SaleItemDTO {

    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal priceAtSale;
    private BigDecimal subtotal;

    public SaleItemDTO(Long productId, String productName, int quantity, BigDecimal priceAtSale) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.priceAtSale = priceAtSale;
        this.subtotal = priceAtSale.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPriceAtSale() { return priceAtSale; }
    public BigDecimal getSubtotal() { return subtotal; }
}
