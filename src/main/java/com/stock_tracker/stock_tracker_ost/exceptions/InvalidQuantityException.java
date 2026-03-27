package com.stock_tracker.stock_tracker_ost.exceptions;

public class InvalidQuantityException extends RuntimeException {
    public InvalidQuantityException() {
        super("Ilość musi być większa od 0");
    }
}
