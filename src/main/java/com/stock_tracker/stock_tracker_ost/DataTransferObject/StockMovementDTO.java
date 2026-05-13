package com.stock_tracker.stock_tracker_ost.DataTransferObject;

import com.stock_tracker.stock_tracker_ost.model.MovementType;
import java.time.LocalDateTime;

public class StockMovementDTO {

    private int quantity;
    private MovementType type;
    private LocalDateTime date;

    public StockMovementDTO(int quantity, MovementType type, LocalDateTime date) {
        this.quantity = quantity;
        this.type = type;
        this.date = date;
    }

    public int getQuantity() { return quantity; }
    public MovementType getType() { return type; }
    public LocalDateTime getDate() { return date; }
}