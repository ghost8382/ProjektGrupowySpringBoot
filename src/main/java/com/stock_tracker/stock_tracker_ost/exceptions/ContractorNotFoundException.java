package com.stock_tracker.stock_tracker_ost.exceptions;

public class ContractorNotFoundException extends RuntimeException {
    public ContractorNotFoundException(Long id) {
        super("Kontrahent o ID " + id + " nie zostal znaleziony");
    }
}
