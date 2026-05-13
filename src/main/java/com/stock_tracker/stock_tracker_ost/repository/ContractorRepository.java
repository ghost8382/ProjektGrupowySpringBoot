package com.stock_tracker.stock_tracker_ost.repository;

import com.stock_tracker.stock_tracker_ost.model.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractorRepository extends JpaRepository<Contractor, Long> {
    List<Contractor> findByNameContainingIgnoreCase(String name);
    boolean existsByNip(String nip);
    boolean existsByNipAndIdNot(String nip, Long id);
}
