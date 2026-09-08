package com.emergency.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "pharmacies", indexes = {
    @Index(name = "idx_city", columnList = "city"),
    @Index(name = "idx_location", columnList = "latitude, longitude")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pharmacyId;

    @Column(nullable = false)
    private String pharmacyName;

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
    private LocalTime openingTime;
    private LocalTime closingTime;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean is24Hours = false;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean hasDelivery = false;

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
