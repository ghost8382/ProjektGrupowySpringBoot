package com.stock_tracker.stock_tracker_ost.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Użytkownik o id " + id + " nie istnieje");
    }
}
