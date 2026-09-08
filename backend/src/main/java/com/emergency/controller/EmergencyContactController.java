package com.emergency.controller;

import com.emergency.model.EmergencyContact;
import com.emergency.service.EmergencyContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/emergency-contacts")
@Slf4j
@CrossOrigin(origins = "*")
public class EmergencyContactController {
    @Autowired
    private EmergencyContactService emergencyContactService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EmergencyContact>> getUserEmergencyContacts(@PathVariable Integer userId) {
        return ResponseEntity.ok(emergencyContactService.getUserEmergencyContacts(userId));
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<?> getEmergencyContactById(@PathVariable Integer contactId) {
        return emergencyContactService.getEmergencyContactById(contactId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact not found"));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> addEmergencyContact(@PathVariable Integer userId, @RequestBody EmergencyContact contact) {
        try {
            EmergencyContact newContact = emergencyContactService.addEmergencyContact(userId, contact);
            return ResponseEntity.status(HttpStatus.CREATED).body(newContact);
        } catch (Exception e) {
            log.error("Error adding emergency contact: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/user/{userId}/contact/{contactId}")
    public ResponseEntity<?> updateEmergencyContact(
            @PathVariable Integer userId,
            @PathVariable Integer contactId,
            @RequestBody EmergencyContact contactDetails) {
        try {
            EmergencyContact updatedContact = emergencyContactService.updateEmergencyContact(userId, contactId, contactDetails);
            return ResponseEntity.ok(updatedContact);
        } catch (Exception e) {
            log.error("Error updating emergency contact: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/user/{userId}/contact/{contactId}")
    public ResponseEntity<?> deleteEmergencyContact(@PathVariable Integer userId, @PathVariable Integer contactId) {
        try {
            emergencyContactService.deleteEmergencyContact(userId, contactId);
            return ResponseEntity.ok("Emergency contact deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting emergency contact: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
