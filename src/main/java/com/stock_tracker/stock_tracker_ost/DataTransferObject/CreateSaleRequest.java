package com.stock_tracker.stock_tracker_ost.DataTransferObject;

import java.util.List;

public class CreateSaleRequest {

    private Long userId;
    private List<SaleItemRequest> items;

    public Long getUserId() { return userId; }
    public List<SaleItemRequest> getItems() { return items; }

    public void setUserId(Long userId) { this.userId = userId; }
    public void setItems(List<SaleItemRequest> items) { this.items = items; }
}
