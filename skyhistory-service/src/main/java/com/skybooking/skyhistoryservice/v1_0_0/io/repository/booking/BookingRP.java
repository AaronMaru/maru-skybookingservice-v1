package com.skybooking.skyhistoryservice.v1_0_0.io.repository.booking;

import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.booking.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRP extends JpaRepository<BookingEntity, Long> {

    @Query(value = "SELECT * FROM bookings WHERE status in (1, 3, 10, 11, 12)", nativeQuery = true)
    List<BookingEntity> getBookings();

}
