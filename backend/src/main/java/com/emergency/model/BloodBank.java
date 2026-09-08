package com.emergency.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blood_banks", indexes = {
    @Index(name = "idx_city", columnList = "city"),
    @Index(name = "idx_location", columnList = "latitude, longitude")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bankId;

    @Column(nullable = false)
    private String bankName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    private String state;
    private String postalCode;
    private String country = "India";

    @Column(nullable = false, precision = 10, scale = 8)
    private Double latitude;

    @Column(nullable = false, precision = 11, scale = 8)
    private Double longitude;

    @Column(nullable = false)
    private String phoneNumber;

    private String email;
    private String operatingHours;
    private String contactPerson;

    @Column(columnDefinition = "TEXT")
    private String services;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
