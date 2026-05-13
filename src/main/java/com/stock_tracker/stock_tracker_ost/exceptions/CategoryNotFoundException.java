package com.stock_tracker.stock_tracker_ost.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super("Kategoria o id " + id + " nie istnieje");
    }
}
