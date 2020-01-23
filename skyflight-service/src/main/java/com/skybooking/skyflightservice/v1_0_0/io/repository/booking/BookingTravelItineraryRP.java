package com.skybooking.skyflightservice.v1_0_0.io.repository.booking;

import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingTravelItineraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingTravelItineraryRP extends JpaRepository<BookingTravelItineraryEntity, Integer> {
}
