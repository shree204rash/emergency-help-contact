package com.emergency.controller;

import com.emergency.model.FireStation;
import com.emergency.service.FireStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fire")
@Slf4j
@CrossOrigin(origins = "*")
public class FireStationController {
    @Autowired
    private FireStationService fireStationService;

    @GetMapping
    public ResponseEntity<List<FireStation>> getAllFireStations() {
        return ResponseEntity.ok(fireStationService.getAllFireStations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFireStationById(@PathVariable Integer id) {
        return fireStationService.getFireStationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fire station not found"));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<FireStation>> getFireStationsByCity(@PathVariable String city) {
        return ResponseEntity.ok(fireStationService.getFireStationsByCity(city));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<FireStation>> getNearbyFireStations(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam String city,
            @RequestParam(defaultValue = "5") Integer limit) {
        List<FireStation> stations = fireStationService.getNearbyFireStations(latitude, longitude, city, limit);
        return ResponseEntity.ok(stations);
    }

    @GetMapping("/distance")
    public ResponseEntity<List<FireStation>> getFireStationsByDistance(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "5") Double radius) {
        List<FireStation> stations = fireStationService.getFireStationsByDistance(latitude, longitude, radius);
        return ResponseEntity.ok(stations);
    }

    @PostMapping
    public ResponseEntity<?> addFireStation(@RequestBody FireStation station) {
        try {
            FireStation newStation = fireStationService.addFireStation(station);
            return ResponseEntity.status(HttpStatus.CREATED).body(newStation);
        } catch (Exception e) {
            log.error("Error adding fire station: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFireStation(@PathVariable Integer id, @RequestBody FireStation stationDetails) {
        try {
            FireStation updatedStation = fireStationService.updateFireStation(id, stationDetails);
            return ResponseEntity.ok(updatedStation);
        } catch (Exception e) {
            log.error("Error updating fire station: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFireStation(@PathVariable Integer id) {
        try {
            fireStationService.deleteFireStation(id);
            return ResponseEntity.ok("Fire station deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting fire station: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
