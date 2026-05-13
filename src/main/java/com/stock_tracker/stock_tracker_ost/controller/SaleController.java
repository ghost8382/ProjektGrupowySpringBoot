package com.stock_tracker.stock_tracker_ost.controller;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.CreateSaleRequest;
import com.stock_tracker.stock_tracker_ost.DataTransferObject.SaleDTO;
import com.stock_tracker.stock_tracker_ost.service.PdfService;
import com.stock_tracker.stock_tracker_ost.service.SaleService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "http://localhost:4200")
public class SaleController {

    private final SaleService saleService;
    private final PdfService pdfService;

    public SaleController(SaleService saleService, PdfService pdfService) {
        this.saleService = saleService;
        this.pdfService = pdfService;
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

    @GetMapping("/{id}/pdf/receipt")
    public ResponseEntity<byte[]> getReceipt(@PathVariable Long id) {
        byte[] pdf = pdfService.generateReceipt(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"paragon_" + id + ".pdf\"")
                .body(pdf);
    }

    @GetMapping("/{id}/pdf/invoice")
    public ResponseEntity<byte[]> getInvoice(@PathVariable Long id) {
        byte[] pdf = pdfService.generateInvoice(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"faktura_" + id + ".pdf\"")
                .body(pdf);
    }
}
