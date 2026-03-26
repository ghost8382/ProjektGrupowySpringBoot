package com.stock_tracker.stock_tracker.service;

import com.stock_tracker.stock_tracker.DataTransferObject.ProductDTO;
import com.stock_tracker.stock_tracker.DataTransferObject.StockMovementDTO;
import com.stock_tracker.stock_tracker.exceptions.InvalidQuantityException;
import com.stock_tracker.stock_tracker.exceptions.NotEnoughStockException;
import com.stock_tracker.stock_tracker.exceptions.ProductNotFoundException;
import com.stock_tracker.stock_tracker.model.MovementType;
import com.stock_tracker.stock_tracker.model.Product;
import com.stock_tracker.stock_tracker.model.StockMovement;
import com.stock_tracker.stock_tracker.repository.ProductRepository;
import com.stock_tracker.stock_tracker.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public ProductService(ProductRepository productRepository, StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    public List<ProductDTO> getAll(){
        return productRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public Product add(Product product){
        if (product.getQuantity() < 0){
            throw new RuntimeException("Ilość nie może być ujemna");
        }
        return productRepository.save(product);
    }


    public void delete(Long id){
        productRepository.deleteById(id);
    }

    public Product addStock(Long id, int quantity){
        if (quantity <= 0) {
            throw new RuntimeException("Ilość musi być większa od 0");
        }

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

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

        if (quantity <= 0) {
            throw new InvalidQuantityException();
        }

        Product product = productRepository.findById(id).orElseThrow();

        if (product.getQuantity() < quantity) {
            throw new NotEnoughStockException();
        }

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
                product.getPrice()
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
