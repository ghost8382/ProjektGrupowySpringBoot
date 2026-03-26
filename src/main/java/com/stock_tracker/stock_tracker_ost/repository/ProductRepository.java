package com.stock_tracker.stock_tracker.repository;

import com.stock_tracker.stock_tracker.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
