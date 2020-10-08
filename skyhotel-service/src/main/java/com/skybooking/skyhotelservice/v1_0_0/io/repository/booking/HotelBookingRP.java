package com.skybooking.skyhotelservice.v1_0_0.io.repository.booking;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.HotelBookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelBookingRP extends JpaRepository<HotelBookingEntity, Long> {

    @Query(value = "SELECT * FROM hotel_booking WHERE code = ?1 AND status IN (?2, ?3, ?4, ?5) LIMIT 1", nativeQuery = true)
    HotelBookingEntity getPnrCreated(String code, String pending, String paymentSelected, String paymentCreated, String paymentProcessing);

    HotelBookingEntity findByCode(String code);

    @Query(value = "SELECT * FROM hotel_booking ORDER BY id DESC LIMIT 1", nativeQuery = true)
    HotelBookingEntity findFirstByOrderByIdDesc();

}
