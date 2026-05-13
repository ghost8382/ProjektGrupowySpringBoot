package com.stock_tracker.stock_tracker_ost.controller;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.ContractorDTO;
import com.stock_tracker.stock_tracker_ost.service.ContractorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contractors")
@CrossOrigin(origins = "http://localhost:4200")
public class ContractorController {

    private final ContractorService contractorService;

    public ContractorController(ContractorService contractorService) {
        this.contractorService = contractorService;
    }

    @GetMapping
    public List<ContractorDTO> getAll(@RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return contractorService.search(search);
        }
        return contractorService.getAll();
    }

    @GetMapping("/{id}")
    public ContractorDTO getById(@PathVariable Long id) {
        return contractorService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContractorDTO create(@RequestBody ContractorDTO dto) {
        return contractorService.create(dto);
    }

    @PutMapping("/{id}")
    public ContractorDTO update(@PathVariable Long id, @RequestBody ContractorDTO dto) {
        return contractorService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        contractorService.delete(id);
    }
}
