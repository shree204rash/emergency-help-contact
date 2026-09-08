package com.emergency.repository;

import com.emergency.model.BloodStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BloodStockRepository extends JpaRepository<BloodStock, Integer> {
    List<BloodStock> findByBloodBankId(Integer bankId);
    Optional<BloodStock> findByBloodBankIdAndBloodType(Integer bankId, String bloodType);
}
