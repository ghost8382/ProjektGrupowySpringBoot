package com.stock_tracker.stock_tracker.controller;

import com.stock_tracker.stock_tracker.DataTransferObject.ProductDTO;
import com.stock_tracker.stock_tracker.DataTransferObject.StockMovementDTO;
import com.stock_tracker.stock_tracker.model.Product;
import com.stock_tracker.stock_tracker.model.StockMovement;
import com.stock_tracker.stock_tracker.repository.ProductRepository;
import com.stock_tracker.stock_tracker.service.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

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
    public List<ProductDTO> getAll() {
        return productService.getAll();
    }

    @PostMapping
    public Product add(@RequestBody Product product) { //trzeba bedzie podmienic to na warstwe serwisu, bo aktualnie brak walidacji, sama baza danych

       return productService.add(product);
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
