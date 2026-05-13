package com.stock_tracker.stock_tracker_ost.service;

import com.stock_tracker.stock_tracker_ost.DataTransferObject.ContractorDTO;
import com.stock_tracker.stock_tracker_ost.exceptions.ContractorNotFoundException;
import com.stock_tracker.stock_tracker_ost.model.Contractor;
import com.stock_tracker.stock_tracker_ost.repository.ContractorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractorService {

    private final ContractorRepository contractorRepository;

    public ContractorService(ContractorRepository contractorRepository) {
        this.contractorRepository = contractorRepository;
    }

    public List<ContractorDTO> getAll() {
        return contractorRepository.findAll().stream().map(this::toDTO).toList();
    }

    public List<ContractorDTO> search(String name) {
        return contractorRepository.findByNameContainingIgnoreCase(name).stream().map(this::toDTO).toList();
    }

    public ContractorDTO getById(Long id) {
        return toDTO(contractorRepository.findById(id)
                .orElseThrow(() -> new ContractorNotFoundException(id)));
    }

    public ContractorDTO create(ContractorDTO dto) {
        if (dto.getNip() != null && !dto.getNip().isBlank()
                && contractorRepository.existsByNip(dto.getNip())) {
            throw new RuntimeException("Kontrahent z NIP '" + dto.getNip() + "' juz istnieje");
        }
        Contractor c = fromDTO(new Contractor(), dto);
        return toDTO(contractorRepository.save(c));
    }

    public ContractorDTO update(Long id, ContractorDTO dto) {
        Contractor c = contractorRepository.findById(id)
                .orElseThrow(() -> new ContractorNotFoundException(id));
        if (dto.getNip() != null && !dto.getNip().isBlank()
                && contractorRepository.existsByNipAndIdNot(dto.getNip(), id)) {
            throw new RuntimeException("Kontrahent z NIP '" + dto.getNip() + "' juz istnieje");
        }
        return toDTO(contractorRepository.save(fromDTO(c, dto)));
    }

    public void delete(Long id) {
        if (!contractorRepository.existsById(id)) {
            throw new ContractorNotFoundException(id);
        }
        contractorRepository.deleteById(id);
    }

    private Contractor fromDTO(Contractor c, ContractorDTO dto) {
        c.setName(dto.getName());
        c.setNip(dto.getNip());
        c.setAddress(dto.getAddress());
        c.setCity(dto.getCity());
        c.setPostalCode(dto.getPostalCode());
        c.setPhone(dto.getPhone());
        c.setEmail(dto.getEmail());
        return c;
    }

    private ContractorDTO toDTO(Contractor c) {
        return new ContractorDTO(c.getId(), c.getName(), c.getNip(),
                c.getAddress(), c.getCity(), c.getPostalCode(), c.getPhone(), c.getEmail());
    }
}
