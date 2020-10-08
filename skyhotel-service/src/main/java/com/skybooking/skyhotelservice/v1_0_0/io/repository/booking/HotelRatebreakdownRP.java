package com.skybooking.skyhotelservice.v1_0_0.io.repository.booking;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.HotelBookingRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRatebreakdownRP extends JpaRepository<HotelBookingRateEntity, Long> {
}
