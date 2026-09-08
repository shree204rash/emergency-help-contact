package com.emergency.service;

import com.emergency.model.BloodBank;
import com.emergency.model.BloodStock;
import com.emergency.repository.BloodBankRepository;
import com.emergency.repository.BloodStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BloodBankService {
    @Autowired
    private BloodBankRepository bloodBankRepository;

    @Autowired
    private BloodStockRepository bloodStockRepository;

    public List<BloodBank> getAllBloodBanks() {
        return bloodBankRepository.findAll();
    }

    public Optional<BloodBank> getBloodBankById(Integer id) {
        return bloodBankRepository.findById(id);
    }

    public List<BloodBank> getBloodBanksByCity(String city) {
        return bloodBankRepository.findByCity(city);
    }

    public List<BloodBank> getBloodBanksByState(String state) {
        return bloodBankRepository.findByState(state);
    }

    public List<BloodBank> getNearbyBloodBanks(Double latitude, Double longitude, String city, Integer limit) {
        log.info("Searching for nearby blood banks at {}, {}", latitude, longitude);
        return bloodBankRepository.findNearbyBloodBanks(latitude, longitude, city, limit);
    }

    public List<BloodBank> getBloodBanksByDistance(Double latitude, Double longitude, Double radius) {
        log.info("Searching blood banks within {} km radius", radius);
        return bloodBankRepository.findBloodBanksByDistance(latitude, longitude, radius);
    }

    public BloodBank addBloodBank(BloodBank bank) {
        log.info("Adding new blood bank: {}", bank.getBankName());
        return bloodBankRepository.save(bank);
    }

    public BloodBank updateBloodBank(Integer id, BloodBank bankDetails) {
        BloodBank bank = bloodBankRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blood bank not found"));
        if (bankDetails.getBankName() != null) bank.setBankName(bankDetails.getBankName());
        if (bankDetails.getPhoneNumber() != null) bank.setPhoneNumber(bankDetails.getPhoneNumber());
        if (bankDetails.getServices() != null) bank.setServices(bankDetails.getServices());
        log.info("Blood bank updated: {}", id);
        return bloodBankRepository.save(bank);
    }

    public void deleteBloodBank(Integer id) {
        bloodBankRepository.deleteById(id);
        log.info("Blood bank deleted: {}", id);
    }

    public List<BloodStock> getBloodStockByBank(Integer bankId) {
        return bloodStockRepository.findByBloodBankId(bankId);
    }

    public Optional<BloodStock> getBloodStock(Integer bankId, String bloodType) {
        return bloodStockRepository.findByBloodBankIdAndBloodType(bankId, bloodType);
    }

    public BloodStock updateBloodStock(Integer bankId, String bloodType, Integer units) {
        BloodStock stock = bloodStockRepository.findByBloodBankIdAndBloodType(bankId, bloodType)
                .orElseThrow(() -> new RuntimeException("Blood stock not found"));
        stock.setUnitsAvailable(units);
        log.info("Blood stock updated for {} - {} units available", bloodType, units);
        return bloodStockRepository.save(stock);
    }
}
