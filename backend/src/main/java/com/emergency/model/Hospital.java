package com.emergency.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hospitals", indexes = {
    @Index(name = "idx_city", columnList = "city"),
    @Index(name = "idx_location", columnList = "latitude, longitude")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hospitalId;

    @Column(nullable = false)
    private String hospitalName;

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

    private String emergencyNumber;
    private String website;
    private String email;
    private String operatingHours;

    private Integer bedsTotal;
    private Integer bedsAvailable;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean hasAmbulance = true;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean hasICU = false;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean hasTraumaCenter = false;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean bloodBankAvailable = false;

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
