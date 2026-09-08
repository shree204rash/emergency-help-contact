package com.emergency.repository;

import com.emergency.model.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Integer> {
    List<Pharmacy> findByCity(String city);
    List<Pharmacy> findByState(String state);
    List<Pharmacy> findByIs24Hours(Boolean is24Hours);
    
    @Query(value = "SELECT * FROM pharmacies WHERE city = :city " +
           "ORDER BY SQRT(POW(latitude - :latitude, 2) + POW(longitude - :longitude, 2)) ASC LIMIT :limit",
           nativeQuery = true)
    List<Pharmacy> findNearbyPharmacies(@Param("latitude") Double latitude,
                                       @Param("longitude") Double longitude,
                                       @Param("city") String city,
                                       @Param("limit") Integer limit);
    
    @Query(value = "SELECT * FROM pharmacies WHERE " +
           "SQRT(POW(latitude - :latitude, 2) + POW(longitude - :longitude, 2)) * 111 <= :radius " +
           "ORDER BY SQRT(POW(latitude - :latitude, 2) + POW(longitude - :longitude, 2)) ASC",
           nativeQuery = true)
    List<Pharmacy> findPharmaciesByDistance(@Param("latitude") Double latitude,
                                           @Param("longitude") Double longitude,
                                           @Param("radius") Double radius);
}
