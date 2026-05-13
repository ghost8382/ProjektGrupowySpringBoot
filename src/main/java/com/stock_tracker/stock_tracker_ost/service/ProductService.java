package com.stock_tracker.stock_tracker_ost.service;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.ProductDTO;
import com.stock_tracker.stock_tracker_ost.DataTransferObject.StockMovementDTO;
import com.stock_tracker.stock_tracker_ost.exceptions.InvalidQuantityException;
import com.stock_tracker.stock_tracker_ost.exceptions.NotEnoughStockException;
import com.stock_tracker.stock_tracker_ost.exceptions.ProductNotFoundException;
import com.stock_tracker.stock_tracker_ost.model.*;
import com.stock_tracker.stock_tracker_ost.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          StockMovementRepository stockMovementRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<ProductDTO> getAll(Long categoryId, String name, Pageable pageable) {

        Page<Product> page;

        if (categoryId != null && name != null) {
            page = productRepository.findByCategoryIdAndNameContainingIgnoreCase(categoryId, name, pageable);
        } else if (categoryId != null) {
            page = productRepository.findByCategoryId(categoryId, pageable);
        } else if (name != null) {
            page = productRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            page = productRepository.findAll(pageable);
        }

        return page.map(this::mapToDTO);
    }

    public Product add(Product product, Long categoryId) {
        if (product.getQuantity() < 0) throw new RuntimeException();

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElseThrow();
            product.setCategory(category);
        }

        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Product addStock(Long id, int quantity) {

        if (quantity <= 0) throw new RuntimeException();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setQuantity(product.getQuantity() + quantity);

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setQuantity(quantity);
        movement.setType(MovementType.IN);
        movement.setDate(LocalDateTime.now());

        stockMovementRepository.save(movement);

        return productRepository.save(product);
    }

    public Product removeStock(Long id, int quantity) {

        if (quantity <= 0) throw new InvalidQuantityException();

        Product product = productRepository.findById(id).orElseThrow();

        if (product.getQuantity() < quantity) throw new NotEnoughStockException();

        product.setQuantity(product.getQuantity() - quantity);

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setQuantity(quantity);
        movement.setType(MovementType.OUT);
        movement.setDate(LocalDateTime.now());

        stockMovementRepository.save(movement);

        return productRepository.save(product);
    }

    public List<StockMovementDTO> getMovements(Long productId) {
        return stockMovementRepository.findByProductId(productId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private ProductDTO mapToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getQuantity(),
                product.getPrice(),
                product.getCategory() != null ? product.getCategory().getName() : null
        );
    }

    private StockMovementDTO mapToDTO(StockMovement movement) {
        return new StockMovementDTO(
                movement.getQuantity(),
                movement.getType(),
                movement.getDate()
        );
    }
}