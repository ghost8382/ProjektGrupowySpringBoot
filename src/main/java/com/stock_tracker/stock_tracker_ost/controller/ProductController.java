package com.stock_tracker.stock_tracker_ost.controller;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.ProductDTO;
import com.stock_tracker.stock_tracker_ost.DataTransferObject.StockMovementDTO;
import com.stock_tracker.stock_tracker_ost.model.Product;
import com.stock_tracker.stock_tracker_ost.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<ProductDTO> getAll(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String name,
            Pageable pageable) {

        return productService.getAll(categoryId, name, pageable);
    }

    @PostMapping
    public Product add(@RequestBody Product product,
                       @RequestParam(required = false) Long categoryId) {
        return productService.add(product, categoryId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        productService.delete(id);
    }

    @PostMapping("/{id}/add-stock")
    public Product addStock(@PathVariable Long id, @RequestParam int quantity) {
        return productService.addStock(id, quantity);
    }

    @PostMapping("/{id}/remove-stock")
    public Product removeStock(@PathVariable Long id, @RequestParam int quantity) {
        return productService.removeStock(id, quantity);
    }

    @GetMapping("/{id}/movements")
    public List<StockMovementDTO> getMovements(@PathVariable Long id) {
        return productService.getMovements(id);
    }
}