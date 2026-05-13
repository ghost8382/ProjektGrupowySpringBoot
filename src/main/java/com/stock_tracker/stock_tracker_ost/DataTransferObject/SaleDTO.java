package com.stock_tracker.stock_tracker_ost.DataTransferObject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SaleDTO {

    private Long id;
    private LocalDateTime date;
    private Long userId;
    private String username;
    private Long contractorId;
    private String contractorName;
    private List<SaleItemDTO> items;
    private BigDecimal totalAmount;

    public SaleDTO(Long id, LocalDateTime date, Long userId, String username,
                   Long contractorId, String contractorName,
                   List<SaleItemDTO> items, BigDecimal totalAmount) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.username = username;
        this.contractorId = contractorId;
        this.contractorName = contractorName;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public Long getId() { return id; }
    public LocalDateTime getDate() { return date; }
    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public Long getContractorId() { return contractorId; }
    public String getContractorName() { return contractorName; }
    public List<SaleItemDTO> getItems() { return items; }
    public BigDecimal getTotalAmount() { return totalAmount; }
}
