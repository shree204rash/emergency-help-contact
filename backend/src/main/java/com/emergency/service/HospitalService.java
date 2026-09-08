package com.emergency.service;

import com.emergency.model.Hospital;
import com.emergency.repository.HospitalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public Optional<Hospital> getHospitalById(Integer id) {
        return hospitalRepository.findById(id);
    }

    public List<Hospital> getHospitalsByCity(String city) {
        return hospitalRepository.findByCity(city);
    }

    public List<Hospital> getHospitalsByState(String state) {
        return hospitalRepository.findByState(state);
    }

    public List<Hospital> getNearbyHospitals(Double latitude, Double longitude, String city, Integer limit) {
        log.info("Searching for nearby hospitals at {}, {}", latitude, longitude);
        return hospitalRepository.findNearbyHospitals(latitude, longitude, city, limit);
    }

    public List<Hospital> getHospitalsByDistance(Double latitude, Double longitude, Double radius) {
        log.info("Searching hospitals within {} km radius", radius);
        return hospitalRepository.findHospitalsByDistance(latitude, longitude, radius);
    }

    public Hospital addHospital(Hospital hospital) {
        log.info("Adding new hospital: {}", hospital.getHospitalName());
        return hospitalRepository.save(hospital);
    }

    public Hospital updateHospital(Integer id, Hospital hospitalDetails) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));
        if (hospitalDetails.getHospitalName() != null) hospital.setHospitalName(hospitalDetails.getHospitalName());
        if (hospitalDetails.getPhoneNumber() != null) hospital.setPhoneNumber(hospitalDetails.getPhoneNumber());
        if (hospitalDetails.getBedsAvailable() != null) hospital.setBedsAvailable(hospitalDetails.getBedsAvailable());
        log.info("Hospital updated: {}", id);
        return hospitalRepository.save(hospital);
    }

    public void deleteHospital(Integer id) {
        hospitalRepository.deleteById(id);
        log.info("Hospital deleted: {}", id);
    }
}
