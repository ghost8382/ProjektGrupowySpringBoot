package com.stock_tracker.stock_tracker_ost.exceptions;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException() {
        super("Za mało na magazynie");
    }
}
