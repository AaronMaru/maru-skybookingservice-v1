package com.skybooking.paymentservice.v1_0_0.io.repository.booking;

import com.skybooking.paymentservice.v1_0_0.io.enitity.booking.HotelBookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelBookingRP extends JpaRepository<HotelBookingEntity, Long> {

    @Query(value = "SELECT * FROM hotel_booking WHERE code = ?1", nativeQuery = true)
    HotelBookingEntity getBooking(String code);

}
