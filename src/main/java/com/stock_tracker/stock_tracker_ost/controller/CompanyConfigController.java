package com.stock_tracker.stock_tracker_ost.controller;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.CompanyConfigDTO;
import com.stock_tracker.stock_tracker_ost.service.CompanyConfigService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyConfigController {

    private final CompanyConfigService companyConfigService;

    public CompanyConfigController(CompanyConfigService companyConfigService) {
        this.companyConfigService = companyConfigService;
    }

    @GetMapping
    public CompanyConfigDTO get() {
        return companyConfigService.get();
    }

    @PutMapping
    public CompanyConfigDTO save(@RequestBody CompanyConfigDTO dto) {
        return companyConfigService.save(dto);
    }
}
