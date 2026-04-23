package com.stock_tracker.stock_tracker_ost.repository;

import com.stock_tracker.stock_tracker_ost.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByUserId(Long userId);
}
