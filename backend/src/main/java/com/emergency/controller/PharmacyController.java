package com.emergency.controller;

import com.emergency.model.Pharmacy;
import com.emergency.service.PharmacyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pharmacies")
@Slf4j
@CrossOrigin(origins = "*")
public class PharmacyController {
    @Autowired
    private PharmacyService pharmacyService;

    @GetMapping
    public ResponseEntity<List<Pharmacy>> getAllPharmacies() {
        return ResponseEntity.ok(pharmacyService.getAllPharmacies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPharmacyById(@PathVariable Integer id) {
        return pharmacyService.getPharmacyById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pharmacy not found"));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Pharmacy>> getPharmaciesByCity(@PathVariable String city) {
        return ResponseEntity.ok(pharmacyService.getPharmaciesByCity(city));
    }

    @GetMapping("/24hours")
    public ResponseEntity<List<Pharmacy>> get24HourPharmacies() {
        return ResponseEntity.ok(pharmacyService.get24HourPharmacies());
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<Pharmacy>> getNearbyPharmacies(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam String city,
            @RequestParam(defaultValue = "5") Integer limit) {
        List<Pharmacy> pharmacies = pharmacyService.getNearbyPharmacies(latitude, longitude, city, limit);
        return ResponseEntity.ok(pharmacies);
    }

    @GetMapping("/distance")
    public ResponseEntity<List<Pharmacy>> getPharmaciesByDistance(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "5") Double radius) {
        List<Pharmacy> pharmacies = pharmacyService.getPharmaciesByDistance(latitude, longitude, radius);
        return ResponseEntity.ok(pharmacies);
    }

    @PostMapping
    public ResponseEntity<?> addPharmacy(@RequestBody Pharmacy pharmacy) {
        try {
            Pharmacy newPharmacy = pharmacyService.addPharmacy(pharmacy);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPharmacy);
        } catch (Exception e) {
            log.error("Error adding pharmacy: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePharmacy(@PathVariable Integer id, @RequestBody Pharmacy pharmacyDetails) {
        try {
            Pharmacy updatedPharmacy = pharmacyService.updatePharmacy(id, pharmacyDetails);
            return ResponseEntity.ok(updatedPharmacy);
        } catch (Exception e) {
            log.error("Error updating pharmacy: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePharmacy(@PathVariable Integer id) {
        try {
            pharmacyService.deletePharmacy(id);
            return ResponseEntity.ok("Pharmacy deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting pharmacy: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
