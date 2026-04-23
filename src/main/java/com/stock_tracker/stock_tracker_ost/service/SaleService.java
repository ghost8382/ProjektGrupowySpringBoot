package com.stock_tracker.stock_tracker_ost.service;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.CreateSaleRequest;
import com.stock_tracker.stock_tracker_ost.DataTransferObject.SaleDTO;
import com.stock_tracker.stock_tracker_ost.DataTransferObject.SaleItemDTO;
import com.stock_tracker.stock_tracker_ost.exceptions.*;
import com.stock_tracker.stock_tracker_ost.model.*;
import com.stock_tracker.stock_tracker_ost.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public SaleService(SaleRepository saleRepository,
                       UserRepository userRepository,
                       ProductRepository productRepository,
                       StockMovementRepository stockMovementRepository) {
        this.saleRepository = saleRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    @Transactional
    public SaleDTO create(CreateSaleRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        Sale sale = new Sale();
        sale.setDate(LocalDateTime.now());
        sale.setUser(user);

        List<SaleItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (var itemReq : request.getItems()) {
            if (itemReq.getQuantity() <= 0) {
                throw new InvalidQuantityException();
            }

            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(itemReq.getProductId()));

            if (product.getQuantity() < itemReq.getQuantity()) {
                throw new NotEnoughStockException();
            }

            product.setQuantity(product.getQuantity() - itemReq.getQuantity());
            productRepository.save(product);

            StockMovement movement = new StockMovement();
            movement.setProduct(product);
            movement.setQuantity(itemReq.getQuantity());
            movement.setType(MovementType.OUT);
            movement.setDate(LocalDateTime.now());
            stockMovementRepository.save(movement);

            SaleItem item = new SaleItem();
            item.setSale(sale);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setPriceAtSale(product.getPrice());
            items.add(item);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
        }

        sale.setItems(items);
        sale.setTotalAmount(total);
        Sale saved = saleRepository.save(sale);

        return toDTO(saved);
    }

    public List<SaleDTO> getAll() {
        return saleRepository.findAll().stream().map(this::toDTO).toList();
    }

    public SaleDTO getById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException(id));
        return toDTO(sale);
    }

    public List<SaleDTO> getByUser(Long userId) {
        return saleRepository.findByUserId(userId).stream().map(this::toDTO).toList();
    }

    private SaleDTO toDTO(Sale sale) {
        List<SaleItemDTO> itemDTOs = sale.getItems().stream()
                .map(i -> new SaleItemDTO(
                        i.getProduct().getId(),
                        i.getProduct().getName(),
                        i.getQuantity(),
                        i.getPriceAtSale()
                ))
                .toList();

        return new SaleDTO(
                sale.getId(),
                sale.getDate(),
                sale.getUser().getId(),
                sale.getUser().getUsername(),
                itemDTOs,
                sale.getTotalAmount()
        );
    }
}
