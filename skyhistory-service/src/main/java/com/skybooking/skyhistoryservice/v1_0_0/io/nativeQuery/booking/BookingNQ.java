package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking;


import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@NativeQueryFolder("bookings")
@Component
public interface BookingNQ extends NativeQuery {

    Page<BookingTO> listBooking(@NativeQueryParam(value = "keyword") String keyword,
                                @NativeQueryParam(value = "bookStatus") String bookStatus,
                                @NativeQueryParam(value = "startRange") Integer startRange,
                                @NativeQueryParam(value = "endRange") Integer endRange,
                                @NativeQueryParam(value = "tripType") String tripType,
                                @NativeQueryParam(value = "className") String className,
                                @NativeQueryParam(value = "bookDate") String bookDate,
                                @NativeQueryParam(value = "paymentType") String paymentType,
                                @NativeQueryParam(value = "flyingFrom") String flyingFrom,
                                @NativeQueryParam(value = "flyingTo") String flyingTo,
                                @NativeQueryParam(value = "bookByName") String bookByName,
                                @NativeQueryParam(value = "action") String action,
                                @NativeQueryParam(value = "role") String role,
                                @NativeQueryParam(value = "skyuserId") Long skyuserId,
                                @NativeQueryParam(value = "companyId") Long companyId,
                                @NativeQueryParam(value = "stake") String stake,
                                Pageable pageable);

    List<BookingTO> detailBooking(@NativeQueryParam(value = "stake") String stake,
                                  @NativeQueryParam(value = "bookingId") Long id,
                                  @NativeQueryParam(value = "skyuserId") Long skyuserId,
                                  @NativeQueryParam(value = "companyId") Long companyId,
                                  @NativeQueryParam(value = "role") String role);

    List<BookingOdTO> listBookingOd(@NativeQueryParam(value = "bookingId") Integer bookingId);
    BookingOdSegTO bookingOdSeg(@NativeQueryParam(value = "bodId") Integer bodId);
    List<BookingTicketsTQ> listTicket(@NativeQueryParam(value = "bookingId") Long bookingId);
    BookingBaggageInfoTQ baggageInfo(@NativeQueryParam(value = "bookingId") Long bookingId,
                                     @NativeQueryParam(value = "passType") String passType);
    List<BookingAirItinPriceTQ> listAirItinPrice(@NativeQueryParam(value = "bookingId") Long bookingId);

}
