package com.emergency.repository;

import com.emergency.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
    List<Hospital> findByCity(String city);
    List<Hospital> findByState(String state);
    
    @Query(value = "SELECT * FROM hospitals WHERE city = :city " +
           "ORDER BY SQRT(POW(latitude - :latitude, 2) + POW(longitude - :longitude, 2)) ASC LIMIT :limit",
           nativeQuery = true)
    List<Hospital> findNearbyHospitals(@Param("latitude") Double latitude,
                                       @Param("longitude") Double longitude,
                                       @Param("city") String city,
                                       @Param("limit") Integer limit);
    
    @Query(value = "SELECT * FROM hospitals WHERE " +
           "SQRT(POW(latitude - :latitude, 2) + POW(longitude - :longitude, 2)) * 111 <= :radius " +
           "ORDER BY SQRT(POW(latitude - :latitude, 2) + POW(longitude - :longitude, 2)) ASC",
           nativeQuery = true)
    List<Hospital> findHospitalsByDistance(@Param("latitude") Double latitude,
                                           @Param("longitude") Double longitude,
                                           @Param("radius") Double radius);
}
