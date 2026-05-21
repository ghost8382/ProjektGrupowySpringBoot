package com.stock_tracker.stock_tracker_ost.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loyalty_transaction")
public class LoyaltyTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loyalty_account_id")
    private LoyaltyAccount loyaltyAccount;

    private int points;

    @Enumerated(EnumType.STRING)
    private LoyaltyTransactionType type;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = true)
    private Sale sale;

    private LocalDateTime date;

    public Long getId() { return id; }
    public LoyaltyAccount getLoyaltyAccount() { return loyaltyAccount; }
    public int getPoints() { return points; }
    public LoyaltyTransactionType getType() { return type; }
    public Sale getSale() { return sale; }
    public LocalDateTime getDate() { return date; }

    public void setId(Long id) { this.id = id; }
    public void setLoyaltyAccount(LoyaltyAccount loyaltyAccount) { this.loyaltyAccount = loyaltyAccount; }
    public void setPoints(int points) { this.points = points; }
    public void setType(LoyaltyTransactionType type) { this.type = type; }
    public void setSale(Sale sale) { this.sale = sale; }
    public void setDate(LocalDateTime date) { this.date = date; }
}
