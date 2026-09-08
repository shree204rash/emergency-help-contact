package com.emergency.controller;

import com.emergency.model.PoliceStation;
import com.emergency.service.PoliceStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/police")
@Slf4j
@CrossOrigin(origins = "*")
public class PoliceStationController {
    @Autowired
    private PoliceStationService policeStationService;

    @GetMapping
    public ResponseEntity<List<PoliceStation>> getAllPoliceStations() {
        return ResponseEntity.ok(policeStationService.getAllPoliceStations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPoliceStationById(@PathVariable Integer id) {
        return policeStationService.getPoliceStationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Police station not found"));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<PoliceStation>> getPoliceStationsByCity(@PathVariable String city) {
        return ResponseEntity.ok(policeStationService.getPoliceStationsByCity(city));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<PoliceStation>> getNearbyPoliceStations(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam String city,
            @RequestParam(defaultValue = "5") Integer limit) {
        List<PoliceStation> stations = policeStationService.getNearbyPoliceStations(latitude, longitude, city, limit);
        return ResponseEntity.ok(stations);
    }

    @GetMapping("/distance")
    public ResponseEntity<List<PoliceStation>> getPoliceStationsByDistance(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "5") Double radius) {
        List<PoliceStation> stations = policeStationService.getPoliceStationsByDistance(latitude, longitude, radius);
        return ResponseEntity.ok(stations);
    }

    @PostMapping
    public ResponseEntity<?> addPoliceStation(@RequestBody PoliceStation station) {
        try {
            PoliceStation newStation = policeStationService.addPoliceStation(station);
            return ResponseEntity.status(HttpStatus.CREATED).body(newStation);
        } catch (Exception e) {
            log.error("Error adding police station: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePoliceStation(@PathVariable Integer id, @RequestBody PoliceStation stationDetails) {
        try {
            PoliceStation updatedStation = policeStationService.updatePoliceStation(id, stationDetails);
            return ResponseEntity.ok(updatedStation);
        } catch (Exception e) {
            log.error("Error updating police station: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePoliceStation(@PathVariable Integer id) {
        try {
            policeStationService.deletePoliceStation(id);
            return ResponseEntity.ok("Police station deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting police station: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
