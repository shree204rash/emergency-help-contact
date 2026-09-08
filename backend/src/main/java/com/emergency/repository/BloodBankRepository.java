package com.emergency.repository;

import com.emergency.model.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, Integer> {
    List<BloodBank> findByCity(String city);
    List<BloodBank> findByState(String state);
    
    @Query(value = "SELECT * FROM blood_banks WHERE city = :city " +
           "ORDER BY SQRT(POW(latitude - :latitude, 2) + POW(longitude - :longitude, 2)) ASC LIMIT :limit",
           nativeQuery = true)
    List<BloodBank> findNearbyBloodBanks(@Param("latitude") Double latitude,
                                        @Param("longitude") Double longitude,
                                        @Param("city") String city,
                                        @Param("limit") Integer limit);
    
    @Query(value = "SELECT * FROM blood_banks WHERE " +
           "SQRT(POW(latitude - :latitude, 2) + POW(longitude - :longitude, 2)) * 111 <= :radius " +
           "ORDER BY SQRT(POW(latitude - :latitude, 2) + POW(longitude - :longitude, 2)) ASC",
           nativeQuery = true)
    List<BloodBank> findBloodBanksByDistance(@Param("latitude") Double latitude,
                                            @Param("longitude") Double longitude,
                                            @Param("radius") Double radius);
}
