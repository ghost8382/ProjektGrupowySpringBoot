package com.stock_tracker.stock_tracker_ost.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Produkt o id " + id + " nie istnieje");
    }
}
