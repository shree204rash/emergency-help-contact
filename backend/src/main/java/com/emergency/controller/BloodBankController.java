package com.emergency.controller;

import com.emergency.model.BloodBank;
import com.emergency.model.BloodStock;
import com.emergency.service.BloodBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/blood-banks")
@Slf4j
@CrossOrigin(origins = "*")
public class BloodBankController {
    @Autowired
    private BloodBankService bloodBankService;

    @GetMapping
    public ResponseEntity<List<BloodBank>> getAllBloodBanks() {
        return ResponseEntity.ok(bloodBankService.getAllBloodBanks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBloodBankById(@PathVariable Integer id) {
        return bloodBankService.getBloodBankById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blood bank not found"));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<BloodBank>> getBloodBanksByCity(@PathVariable String city) {
        return ResponseEntity.ok(bloodBankService.getBloodBanksByCity(city));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<BloodBank>> getNearbyBloodBanks(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam String city,
            @RequestParam(defaultValue = "5") Integer limit) {
        List<BloodBank> banks = bloodBankService.getNearbyBloodBanks(latitude, longitude, city, limit);
        return ResponseEntity.ok(banks);
    }

    @GetMapping("/distance")
    public ResponseEntity<List<BloodBank>> getBloodBanksByDistance(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "5") Double radius) {
        List<BloodBank> banks = bloodBankService.getBloodBanksByDistance(latitude, longitude, radius);
        return ResponseEntity.ok(banks);
    }

    @GetMapping("/{id}/blood-stock")
    public ResponseEntity<List<BloodStock>> getBloodStock(@PathVariable Integer id) {
        return ResponseEntity.ok(bloodBankService.getBloodStockByBank(id));
    }

    @PostMapping
    public ResponseEntity<?> addBloodBank(@RequestBody BloodBank bank) {
        try {
            BloodBank newBank = bloodBankService.addBloodBank(bank);
            return ResponseEntity.status(HttpStatus.CREATED).body(newBank);
        } catch (Exception e) {
            log.error("Error adding blood bank: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBloodBank(@PathVariable Integer id, @RequestBody BloodBank bankDetails) {
        try {
            BloodBank updatedBank = bloodBankService.updateBloodBank(id, bankDetails);
            return ResponseEntity.ok(updatedBank);
        } catch (Exception e) {
            log.error("Error updating blood bank: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBloodBank(@PathVariable Integer id) {
        try {
            bloodBankService.deleteBloodBank(id);
            return ResponseEntity.ok("Blood bank deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting blood bank: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
