package com.skybooking.skyflightservice.v1_0_0.io.repository.booking;

import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingOriginDestinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingOriginDestinationRP extends JpaRepository<BookingOriginDestinationEntity, Integer> {
    @Query(value = "SELECT * FROM booking_origin_destinations WHERE parent_id IS NULL AND booking_id = ?", nativeQuery = true)
    List<BookingOriginDestinationEntity> getOriginDestinationParent(Integer bookingId);
}
