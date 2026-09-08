package com.emergency.service;

import com.emergency.model.EmergencyContact;
import com.emergency.repository.EmergencyContactRepository;
import com.emergency.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmergencyContactService {
    @Autowired
    private EmergencyContactRepository emergencyContactRepository;

    @Autowired
    private UserRepository userRepository;

    public List<EmergencyContact> getUserEmergencyContacts(Integer userId) {
        return emergencyContactRepository.findByUserId(userId);
    }

    public Optional<EmergencyContact> getEmergencyContactById(Integer contactId) {
        return emergencyContactRepository.findById(contactId);
    }

    public EmergencyContact addEmergencyContact(Integer userId, EmergencyContact contact) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        contact.setUser(user);
        log.info("Adding emergency contact for user: {}", userId);
        return emergencyContactRepository.save(contact);
    }

    public EmergencyContact updateEmergencyContact(Integer userId, Integer contactId, EmergencyContact contactDetails) {
        EmergencyContact contact = emergencyContactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));
        
        if (!contact.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        if (contactDetails.getContactName() != null) contact.setContactName(contactDetails.getContactName());
        if (contactDetails.getPhoneNumber() != null) contact.setPhoneNumber(contactDetails.getPhoneNumber());
        if (contactDetails.getEmail() != null) contact.setEmail(contactDetails.getEmail());
        if (contactDetails.getRelationship() != null) contact.setRelationship(contactDetails.getRelationship());
        
        log.info("Emergency contact updated: {}", contactId);
        return emergencyContactRepository.save(contact);
    }

    public void deleteEmergencyContact(Integer userId, Integer contactId) {
        emergencyContactRepository.deleteByContactIdAndUserId(contactId, userId);
        log.info("Emergency contact deleted: {}", contactId);
    }
}
