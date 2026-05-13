package com.stock_tracker.stock_tracker_ost.service;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.CompanyConfigDTO;
import com.stock_tracker.stock_tracker_ost.model.CompanyConfig;
import com.stock_tracker.stock_tracker_ost.repository.CompanyConfigRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyConfigService {

    private final CompanyConfigRepository companyConfigRepository;

    public CompanyConfigService(CompanyConfigRepository companyConfigRepository) {
        this.companyConfigRepository = companyConfigRepository;
    }

    public CompanyConfigDTO get() {
        return companyConfigRepository.findById(1L)
                .map(this::toDTO)
                .orElse(new CompanyConfigDTO());
    }

    public CompanyConfigDTO save(CompanyConfigDTO dto) {
        CompanyConfig config = companyConfigRepository.findById(1L).orElse(new CompanyConfig());
        config.setName(dto.getName());
        config.setNip(dto.getNip());
        config.setAddress(dto.getAddress());
        config.setCity(dto.getCity());
        config.setPostalCode(dto.getPostalCode());
        config.setPhone(dto.getPhone());
        config.setEmail(dto.getEmail());
        config.setBankAccount(dto.getBankAccount());
        return toDTO(companyConfigRepository.save(config));
    }

    private CompanyConfigDTO toDTO(CompanyConfig c) {
        return new CompanyConfigDTO(c.getName(), c.getNip(), c.getAddress(),
                c.getCity(), c.getPostalCode(), c.getPhone(), c.getEmail(), c.getBankAccount());
    }
}
