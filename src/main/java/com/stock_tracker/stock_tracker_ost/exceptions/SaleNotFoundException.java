package com.stock_tracker.stock_tracker_ost.exceptions;

public class SaleNotFoundException extends RuntimeException {
    public SaleNotFoundException(Long id) {
        super("Sprzedaż o id " + id + " nie istnieje");
    }
}
