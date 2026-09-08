package com.emergency.controller;

import com.emergency.model.Hospital;
import com.emergency.service.HospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
@Slf4j
@CrossOrigin(origins = "*")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;

    @GetMapping
    public ResponseEntity<List<Hospital>> getAllHospitals() {
        return ResponseEntity.ok(hospitalService.getAllHospitals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHospitalById(@PathVariable Integer id) {
        return hospitalService.getHospitalById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hospital not found"));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Hospital>> getHospitalsByCity(@PathVariable String city) {
        return ResponseEntity.ok(hospitalService.getHospitalsByCity(city));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<Hospital>> getNearbyHospitals(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam String city,
            @RequestParam(defaultValue = "5") Integer limit) {
        List<Hospital> hospitals = hospitalService.getNearbyHospitals(latitude, longitude, city, limit);
        return ResponseEntity.ok(hospitals);
    }

    @GetMapping("/distance")
    public ResponseEntity<List<Hospital>> getHospitalsByDistance(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "5") Double radius) {
        List<Hospital> hospitals = hospitalService.getHospitalsByDistance(latitude, longitude, radius);
        return ResponseEntity.ok(hospitals);
    }

    @PostMapping
    public ResponseEntity<?> addHospital(@RequestBody Hospital hospital) {
        try {
            Hospital newHospital = hospitalService.addHospital(hospital);
            return ResponseEntity.status(HttpStatus.CREATED).body(newHospital);
        } catch (Exception e) {
            log.error("Error adding hospital: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHospital(@PathVariable Integer id, @RequestBody Hospital hospitalDetails) {
        try {
            Hospital updatedHospital = hospitalService.updateHospital(id, hospitalDetails);
            return ResponseEntity.ok(updatedHospital);
        } catch (Exception e) {
            log.error("Error updating hospital: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHospital(@PathVariable Integer id) {
        try {
            hospitalService.deleteHospital(id);
            return ResponseEntity.ok("Hospital deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting hospital: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
