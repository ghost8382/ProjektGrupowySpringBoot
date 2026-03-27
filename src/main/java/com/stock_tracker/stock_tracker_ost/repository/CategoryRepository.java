package com.stock_tracker.stock_tracker_ost.repository;

import com.stock_tracker.stock_tracker_ost.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}