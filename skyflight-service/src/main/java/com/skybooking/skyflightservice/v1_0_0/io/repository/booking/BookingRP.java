package com.skybooking.skyflightservice.v1_0_0.io.repository.booking;

import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRP extends JpaRepository<BookingEntity, Integer> {

    @Query(value = "SELECT booking_code FROM bookings ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String getLatestRow();

    @Query(value = "SELECT * FROM bookings WHERE booking_code = ? AND status IN ( ?, ? )", nativeQuery = true)
    BookingEntity getPnrCreated(String bookingCode, Integer pnrCreated, Integer paymentProcessing);

    @Query(value = "SELECT * FROM bookings WHERE booking_code = ?", nativeQuery = true)
    BookingEntity getBookingByBookingCode(String bookingCode);
}
