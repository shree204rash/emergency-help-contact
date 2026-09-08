package com.emergency.service;

import com.emergency.model.Pharmacy;
import com.emergency.repository.PharmacyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PharmacyService {
    @Autowired
    private PharmacyRepository pharmacyRepository;

    public List<Pharmacy> getAllPharmacies() {
        return pharmacyRepository.findAll();
    }

    public Optional<Pharmacy> getPharmacyById(Integer id) {
        return pharmacyRepository.findById(id);
    }

    public List<Pharmacy> getPharmaciesByCity(String city) {
        return pharmacyRepository.findByCity(city);
    }

    public List<Pharmacy> getPharmaciesByState(String state) {
        return pharmacyRepository.findByState(state);
    }

    public List<Pharmacy> get24HourPharmacies() {
        return pharmacyRepository.findByIs24Hours(true);
    }

    public List<Pharmacy> getNearbyPharmacies(Double latitude, Double longitude, String city, Integer limit) {
        log.info("Searching for nearby pharmacies at {}, {}", latitude, longitude);
        return pharmacyRepository.findNearbyPharmacies(latitude, longitude, city, limit);
    }

    public List<Pharmacy> getPharmaciesByDistance(Double latitude, Double longitude, Double radius) {
        log.info("Searching pharmacies within {} km radius", radius);
        return pharmacyRepository.findPharmaciesByDistance(latitude, longitude, radius);
    }

    public Pharmacy addPharmacy(Pharmacy pharmacy) {
        log.info("Adding new pharmacy: {}", pharmacy.getPharmacyName());
        return pharmacyRepository.save(pharmacy);
    }

    public Pharmacy updatePharmacy(Integer id, Pharmacy pharmacyDetails) {
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pharmacy not found"));
        if (pharmacyDetails.getPharmacyName() != null) pharmacy.setPharmacyName(pharmacyDetails.getPharmacyName());
        if (pharmacyDetails.getPhoneNumber() != null) pharmacy.setPhoneNumber(pharmacyDetails.getPhoneNumber());
        if (pharmacyDetails.getIs24Hours() != null) pharmacy.setIs24Hours(pharmacyDetails.getIs24Hours());
        log.info("Pharmacy updated: {}", id);
        return pharmacyRepository.save(pharmacy);
    }

    public void deletePharmacy(Integer id) {
        pharmacyRepository.deleteById(id);
        log.info("Pharmacy deleted: {}", id);
    }
}
