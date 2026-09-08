package com.emergency.repository;

import com.emergency.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Integer> {
    List<FireStation> findByCity(String city);
    List<FireStation> findByState(String state);
    
    @Query(value = "SELECT * FROM fire_stations WHERE city = :city " +
           "ORDER BY SQRT(POW(latitude - :latitude, 2) + POW(longitude - :longitude, 2)) ASC LIMIT :limit",
           nativeQuery = true)
    List<FireStation> findNearbyFireStations(@Param("latitude") Double latitude,
                                            @Param("longitude") Double longitude,
                                            @Param("city") String city,
                                            @Param("limit") Integer limit);
    
    @Query(value = "SELECT * FROM fire_stations WHERE " +
           "SQRT(POW(latitude - :latitude, 2) + POW(longitude - :longitude, 2)) * 111 <= :radius " +
           "ORDER BY SQRT(POW(latitude - :latitude, 2) + POW(longitude - :longitude, 2)) ASC",
           nativeQuery = true)
    List<FireStation> findStationsByDistance(@Param("latitude") Double latitude,
                                             @Param("longitude") Double longitude,
                                             @Param("radius") Double radius);
}
