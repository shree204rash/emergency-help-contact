package com.emergency.service;

import com.emergency.model.FireStation;
import com.emergency.repository.FireStationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FireStationService {
    @Autowired
    private FireStationRepository fireStationRepository;

    public List<FireStation> getAllFireStations() {
        return fireStationRepository.findAll();
    }

    public Optional<FireStation> getFireStationById(Integer id) {
        return fireStationRepository.findById(id);
    }

    public List<FireStation> getFireStationsByCity(String city) {
        return fireStationRepository.findByCity(city);
    }

    public List<FireStation> getFireStationsByState(String state) {
        return fireStationRepository.findByState(state);
    }

    public List<FireStation> getNearbyFireStations(Double latitude, Double longitude, String city, Integer limit) {
        log.info("Searching for nearby fire stations at {}, {}", latitude, longitude);
        return fireStationRepository.findNearbyFireStations(latitude, longitude, city, limit);
    }

    public List<FireStation> getFireStationsByDistance(Double latitude, Double longitude, Double radius) {
        log.info("Searching fire stations within {} km radius", radius);
        return fireStationRepository.findStationsByDistance(latitude, longitude, radius);
    }

    public FireStation addFireStation(FireStation station) {
        log.info("Adding new fire station: {}", station.getStationName());
        return fireStationRepository.save(station);
    }

    public FireStation updateFireStation(Integer id, FireStation stationDetails) {
        FireStation station = fireStationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fire station not found"));
        if (stationDetails.getStationName() != null) station.setStationName(stationDetails.getStationName());
        if (stationDetails.getPhoneNumber() != null) station.setPhoneNumber(stationDetails.getPhoneNumber());
        if (stationDetails.getEquipmentAvailable() != null) station.setEquipmentAvailable(stationDetails.getEquipmentAvailable());
        log.info("Fire station updated: {}", id);
        return fireStationRepository.save(station);
    }

    public void deleteFireStation(Integer id) {
        fireStationRepository.deleteById(id);
        log.info("Fire station deleted: {}", id);
    }
}
