package com.stock_tracker.stock_tracker_ost.controller;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.CreateSaleRequest;
import com.stock_tracker.stock_tracker_ost.DataTransferObject.SaleDTO;
import com.stock_tracker.stock_tracker_ost.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "http://localhost:4200")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaleDTO create(@RequestBody CreateSaleRequest request) {
        return saleService.create(request);
    }

    @GetMapping
    public List<SaleDTO> getAll() {
        return saleService.getAll();
    }

    @GetMapping("/{id}")
    public SaleDTO getById(@PathVariable Long id) {
        return saleService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<SaleDTO> getByUser(@PathVariable Long userId) {
        return saleService.getByUser(userId);
    }
}
