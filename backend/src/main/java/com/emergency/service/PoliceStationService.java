package com.emergency.service;

import com.emergency.model.PoliceStation;
import com.emergency.repository.PoliceStationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PoliceStationService {
    @Autowired
    private PoliceStationRepository policeStationRepository;

    public List<PoliceStation> getAllPoliceStations() {
        return policeStationRepository.findAll();
    }

    public Optional<PoliceStation> getPoliceStationById(Integer id) {
        return policeStationRepository.findById(id);
    }

    public List<PoliceStation> getPoliceStationsByCity(String city) {
        return policeStationRepository.findByCity(city);
    }

    public List<PoliceStation> getPoliceStationsByState(String state) {
        return policeStationRepository.findByState(state);
    }

    public List<PoliceStation> getNearbyPoliceStations(Double latitude, Double longitude, String city, Integer limit) {
        log.info("Searching for nearby police stations at {}, {}", latitude, longitude);
        return policeStationRepository.findNearbyPoliceStations(latitude, longitude, city, limit);
    }

    public List<PoliceStation> getPoliceStationsByDistance(Double latitude, Double longitude, Double radius) {
        log.info("Searching police stations within {} km radius", radius);
        return policeStationRepository.findStationsByDistance(latitude, longitude, radius);
    }

    public PoliceStation addPoliceStation(PoliceStation station) {
        log.info("Adding new police station: {}", station.getStationName());
        return policeStationRepository.save(station);
    }

    public PoliceStation updatePoliceStation(Integer id, PoliceStation stationDetails) {
        PoliceStation station = policeStationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Police station not found"));
        if (stationDetails.getStationName() != null) station.setStationName(stationDetails.getStationName());
        if (stationDetails.getPhoneNumber() != null) station.setPhoneNumber(stationDetails.getPhoneNumber());
        if (stationDetails.getJurisdictionArea() != null) station.setJurisdictionArea(stationDetails.getJurisdictionArea());
        log.info("Police station updated: {}", id);
        return policeStationRepository.save(station);
    }

    public void deletePoliceStation(Integer id) {
        policeStationRepository.deleteById(id);
        log.info("Police station deleted: {}", id);
    }
}
