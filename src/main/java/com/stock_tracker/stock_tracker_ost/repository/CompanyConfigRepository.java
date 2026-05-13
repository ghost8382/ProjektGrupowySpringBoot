package com.stock_tracker.stock_tracker_ost.repository;

import com.stock_tracker.stock_tracker_ost.model.CompanyConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyConfigRepository extends JpaRepository<CompanyConfig, Long> {
}
