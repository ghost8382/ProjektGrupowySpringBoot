package com.stock_tracker.stock_tracker_ost.controller;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.LoyaltyAccountDTO;
import com.stock_tracker.stock_tracker_ost.DataTransferObject.LoyaltyTransactionDTO;
import com.stock_tracker.stock_tracker_ost.service.LoyaltyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contractors")
@CrossOrigin(origins = "http://localhost:4200")
public class LoyaltyController {

    private final LoyaltyService loyaltyService;

    public LoyaltyController(LoyaltyService loyaltyService) {
        this.loyaltyService = loyaltyService;
    }

    @GetMapping("/{id}/loyalty")
    public LoyaltyAccountDTO getAccount(@PathVariable Long id) {
        return loyaltyService.getAccount(id);
    }

    @GetMapping("/{id}/loyalty/transactions")
    public List<LoyaltyTransactionDTO> getTransactions(@PathVariable Long id) {
        return loyaltyService.getTransactions(id);
    }

    @PostMapping("/{id}/loyalty/redeem")
    public LoyaltyAccountDTO redeem(@PathVariable Long id,
                                     @RequestBody Map<String, Integer> body) {
        int points = body.getOrDefault("points", 0);
        return loyaltyService.redeemPoints(id, points);
    }
}
