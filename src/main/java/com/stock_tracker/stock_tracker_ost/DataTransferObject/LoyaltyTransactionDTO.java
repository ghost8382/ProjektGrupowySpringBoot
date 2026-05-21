package com.stock_tracker.stock_tracker_ost.DataTransferObject;

import com.stock_tracker.stock_tracker_ost.model.LoyaltyTransactionType;
import java.time.LocalDateTime;

public class LoyaltyTransactionDTO {

    private Long id;
    private int points;
    private LoyaltyTransactionType type;
    private Long saleId;
    private LocalDateTime date;

    public LoyaltyTransactionDTO(Long id, int points, LoyaltyTransactionType type,
                                  Long saleId, LocalDateTime date) {
        this.id = id;
        this.points = points;
        this.type = type;
        this.saleId = saleId;
        this.date = date;
    }

    public Long getId() { return id; }
    public int getPoints() { return points; }
    public LoyaltyTransactionType getType() { return type; }
    public Long getSaleId() { return saleId; }
    public LocalDateTime getDate() { return date; }
}
