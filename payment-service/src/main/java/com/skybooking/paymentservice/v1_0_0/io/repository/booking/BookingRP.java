package com.skybooking.paymentservice.v1_0_0.io.repository.booking;


import com.skybooking.paymentservice.v1_0_0.io.enitity.booking.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRP extends JpaRepository<BookingEntity, Integer> {

    @Query(value = "SELECT * FROM bookings WHERE booking_code = ?", nativeQuery = true)
    BookingEntity getBooking(String bookingCode);
}
