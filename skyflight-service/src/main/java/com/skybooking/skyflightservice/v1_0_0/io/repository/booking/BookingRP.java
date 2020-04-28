package com.skybooking.skyflightservice.v1_0_0.io.repository.booking;

import com.skybooking.skyflightservice.v1_0_0.io.entity.booking.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRP extends JpaRepository<BookingEntity, Integer> {

    @Query(value = "SELECT booking_code FROM bookings ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String getLatestRow();

    @Query(value = "SELECT * FROM bookings WHERE booking_code = ? AND status IN ( ?, ?, ?, ? )", nativeQuery = true)
    BookingEntity getPnrCreated(String bookingCode, Integer pnrCreated, Integer selected, Integer created, Integer paymentProcessing);

    @Query(value = "SELECT * FROM bookings WHERE booking_code = ?", nativeQuery = true)
    BookingEntity getBookingByBookingCode(String bookingCode);

    @Query(value = "SELECT * FROM bookings WHERE stakeholder_user_id = :skyuserId AND booking_code = :bookingCode AND status IN (0, 2, 3, 4, 5, 6)", nativeQuery = true)
    BookingEntity getBookingToCancelUser(String bookingCode, Integer skyuserId);

    @Query(value = "SELECT * FROM bookings WHERE stakeholder_company_id = :companyId AND booking_code = :bookingCode AND status IN (0, 2, 3, 4, 5, 6)", nativeQuery = true)
    BookingEntity getBookingToCancelOwner(String bookingCode, Integer companyId);

    @Query(value = "SELECT * FROM bookings WHERE status IN (0, 2, 3, 4, 5, 6) AND created_at < DATE_SUB(UTC_TIMESTAMP(), INTERVAL 1 HOUR)", nativeQuery = true)
    List<BookingEntity> getBookingToCancelOwnerCron();

    BookingEntity findByBookingCode(String code);
}
