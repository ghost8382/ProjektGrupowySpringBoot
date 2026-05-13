package com.stock_tracker.stock_tracker_ost.exceptions;

public class DuplicateCategoryNameException extends RuntimeException {
    public DuplicateCategoryNameException(String name) {
        super("Kategoria o nazwie '" + name + "' juz istnieje w tym poziomie drzewa");
    }
}
