package com.stock_tracker.stock_tracker_ost.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "contractor_id", nullable = true)
    private Contractor contractor;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleItem> items;

    private BigDecimal totalAmount;

    public Long getId() { return id; }
    public LocalDateTime getDate() { return date; }
    public User getUser() { return user; }
    public Contractor getContractor() { return contractor; }
    public List<SaleItem> getItems() { return items; }
    public BigDecimal getTotalAmount() { return totalAmount; }

    public void setId(Long id) { this.id = id; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public void setUser(User user) { this.user = user; }
    public void setContractor(Contractor contractor) { this.contractor = contractor; }
    public void setItems(List<SaleItem> items) { this.items = items; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}
