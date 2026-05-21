package com.stock_tracker.stock_tracker_ost.model;

import jakarta.persistence.*;

@Entity
@Table(name = "loyalty_account")
public class LoyaltyAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "contractor_id", unique = true)
    private Contractor contractor;

    private int totalPoints;

    public Long getId() { return id; }
    public Contractor getContractor() { return contractor; }
    public int getTotalPoints() { return totalPoints; }

    public void setId(Long id) { this.id = id; }
    public void setContractor(Contractor contractor) { this.contractor = contractor; }
    public void setTotalPoints(int totalPoints) { this.totalPoints = totalPoints; }
}
