package com.skybooking.skyhistoryservice.v1_0_0.io.repository.flight;

import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.flight.FlightSaveODEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightSaveODRP extends JpaRepository<FlightSaveODEntity, Long> {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Query to get flight save origin destination by user's id and keyword
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param userId
     * @param keyword
     * @param pageable
     * @return page result
     */
    @Query(
            value = "SELECT * FROM flight_save_origin_destinations WHERE user_id = :userId " +
                    "AND ( airline_code LIKE CONCAT('%',:keyword,'%') OR airline_name LIKE CONCAT('%',:keyword,'%') " +
                    "OR a_date_time LIKE CONCAT('%',:keyword,'%') OR d_date_time LIKE CONCAT('%',:keyword,'%') " +
                    "OR o_location LIKE CONCAT('%',:keyword,'%') OR d_location LIKE CONCAT('%',:keyword,'%') " +
                    "OR o_location_name LIKE CONCAT('%',:keyword,'%') OR d_location_name LIKE CONCAT('%',:keyword,'%')) ",
            countQuery = "SELECT COUNT(*) FROM flight_save_origin_destinations WHERE user_id = :userId " +
                    "AND ( airline_code LIKE CONCAT('%',:keyword,'%') OR airline_name LIKE CONCAT('%',:keyword,'%') " +
                    "OR a_date_time LIKE CONCAT('%',:keyword,'%') OR d_date_time LIKE CONCAT('%',:keyword,'%') " +
                    "OR o_location LIKE CONCAT('%',:keyword,'%') OR d_location LIKE CONCAT('%',:keyword,'%') " +
                    "OR o_location_name LIKE CONCAT('%',:keyword,'%') OR d_location_name LIKE CONCAT('%',:keyword,'%')) ",
            nativeQuery = true)
    Page<FlightSaveODEntity> findAllByUserAndKeyword(long userId, String keyword, Pageable pageable);

}
