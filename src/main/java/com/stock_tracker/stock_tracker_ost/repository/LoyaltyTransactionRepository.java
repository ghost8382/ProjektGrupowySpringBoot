package com.stock_tracker.stock_tracker_ost.repository;

import com.stock_tracker.stock_tracker_ost.model.LoyaltyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoyaltyTransactionRepository extends JpaRepository<LoyaltyTransaction, Long> {
    List<LoyaltyTransaction> findByLoyaltyAccountIdOrderByDateDesc(Long loyaltyAccountId);
}
