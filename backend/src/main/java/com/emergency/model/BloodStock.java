package com.emergency.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "blood_stock", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"bank_id", "blood_type"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stockId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", nullable = false)
    private BloodBank bloodBank;

    @Column(nullable = false)
    private String bloodType;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer unitsAvailable = 0;
}
