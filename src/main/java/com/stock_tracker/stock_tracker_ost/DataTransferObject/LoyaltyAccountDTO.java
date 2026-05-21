package com.stock_tracker.stock_tracker_ost.DataTransferObject;

public class LoyaltyAccountDTO {

    private Long id;
    private Long contractorId;
    private String contractorName;
    private int totalPoints;

    public LoyaltyAccountDTO(Long id, Long contractorId, String contractorName, int totalPoints) {
        this.id = id;
        this.contractorId = contractorId;
        this.contractorName = contractorName;
        this.totalPoints = totalPoints;
    }

    public Long getId() { return id; }
    public Long getContractorId() { return contractorId; }
    public String getContractorName() { return contractorName; }
    public int getTotalPoints() { return totalPoints; }
}
