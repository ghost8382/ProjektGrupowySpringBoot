package com.stock_tracker.stock_tracker_ost.repository;

import com.stock_tracker.stock_tracker_ost.model.LoyaltyAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoyaltyAccountRepository extends JpaRepository<LoyaltyAccount, Long> {
    Optional<LoyaltyAccount> findByContractorId(Long contractorId);
}
