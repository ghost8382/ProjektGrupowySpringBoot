package com.stock_tracker.stock_tracker_ost.service;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.LoyaltyAccountDTO;
import com.stock_tracker.stock_tracker_ost.DataTransferObject.LoyaltyTransactionDTO;
import com.stock_tracker.stock_tracker_ost.exceptions.ContractorNotFoundException;
import com.stock_tracker.stock_tracker_ost.model.*;
import com.stock_tracker.stock_tracker_ost.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoyaltyService {

    private final LoyaltyAccountRepository loyaltyAccountRepository;
    private final LoyaltyTransactionRepository loyaltyTransactionRepository;
    private final ContractorRepository contractorRepository;

    public LoyaltyService(LoyaltyAccountRepository loyaltyAccountRepository,
                          LoyaltyTransactionRepository loyaltyTransactionRepository,
                          ContractorRepository contractorRepository) {
        this.loyaltyAccountRepository = loyaltyAccountRepository;
        this.loyaltyTransactionRepository = loyaltyTransactionRepository;
        this.contractorRepository = contractorRepository;
    }

    @Transactional
    public void earnPoints(Contractor contractor, BigDecimal totalAmount, Sale sale) {
        int points = totalAmount.intValue(); // 1 punkt za 1 PLN

        LoyaltyAccount account = loyaltyAccountRepository.findByContractorId(contractor.getId())
                .orElseGet(() -> {
                    LoyaltyAccount newAccount = new LoyaltyAccount();
                    newAccount.setContractor(contractor);
                    newAccount.setTotalPoints(0);
                    return loyaltyAccountRepository.save(newAccount);
                });

        account.setTotalPoints(account.getTotalPoints() + points);
        loyaltyAccountRepository.save(account);

        LoyaltyTransaction transaction = new LoyaltyTransaction();
        transaction.setLoyaltyAccount(account);
        transaction.setPoints(points);
        transaction.setType(LoyaltyTransactionType.EARN);
        transaction.setSale(sale);
        transaction.setDate(LocalDateTime.now());
        loyaltyTransactionRepository.save(transaction);
    }

    @Transactional
    public LoyaltyAccountDTO redeemPoints(Long contractorId, int points) {
        LoyaltyAccount account = loyaltyAccountRepository.findByContractorId(contractorId)
                .orElseThrow(() -> new ContractorNotFoundException(contractorId));

        if (account.getTotalPoints() < points) {
            throw new RuntimeException("Za mało punktów. Dostępne: " + account.getTotalPoints());
        }

        account.setTotalPoints(account.getTotalPoints() - points);
        loyaltyAccountRepository.save(account);

        LoyaltyTransaction transaction = new LoyaltyTransaction();
        transaction.setLoyaltyAccount(account);
        transaction.setPoints(points);
        transaction.setType(LoyaltyTransactionType.REDEEM);
        transaction.setSale(null);
        transaction.setDate(LocalDateTime.now());
        loyaltyTransactionRepository.save(transaction);

        return toDTO(account);
    }

    public LoyaltyAccountDTO getAccount(Long contractorId) {
        if (!contractorRepository.existsById(contractorId)) {
            throw new ContractorNotFoundException(contractorId);
        }
        return loyaltyAccountRepository.findByContractorId(contractorId)
                .map(this::toDTO)
                .orElseGet(() -> {
                    Contractor contractor = contractorRepository.findById(contractorId).get();
                    return new LoyaltyAccountDTO(null, contractorId, contractor.getName(), 0);
                });
    }

    public List<LoyaltyTransactionDTO> getTransactions(Long contractorId) {
        return loyaltyAccountRepository.findByContractorId(contractorId)
                .map(account -> loyaltyTransactionRepository
                        .findByLoyaltyAccountIdOrderByDateDesc(account.getId())
                        .stream().map(this::toTransactionDTO).toList())
                .orElse(List.of());
    }

    private LoyaltyAccountDTO toDTO(LoyaltyAccount account) {
        return new LoyaltyAccountDTO(
                account.getId(),
                account.getContractor().getId(),
                account.getContractor().getName(),
                account.getTotalPoints()
        );
    }

    private LoyaltyTransactionDTO toTransactionDTO(LoyaltyTransaction t) {
        return new LoyaltyTransactionDTO(
                t.getId(),
                t.getPoints(),
                t.getType(),
                t.getSale() != null ? t.getSale().getId() : null,
                t.getDate()
        );
    }
}
