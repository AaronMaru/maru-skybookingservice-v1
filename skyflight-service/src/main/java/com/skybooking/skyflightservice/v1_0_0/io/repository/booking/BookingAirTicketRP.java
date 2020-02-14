package com.skybooking.skyflightservice.v1_0_0.io.repository.booking;

import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingAirTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingAirTicketRP extends JpaRepository<BookingAirTicketEntity, Integer> {

    @Query(value = "SELECT * FROM booking_airtickets WHERE booking_id = ?", nativeQuery = true)
    List<BookingAirTicketEntity> getTickets(Integer booking_id);
}
