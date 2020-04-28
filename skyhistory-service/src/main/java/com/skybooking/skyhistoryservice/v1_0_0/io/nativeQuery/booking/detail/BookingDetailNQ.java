package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking.detail;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.stereotype.Component;

import java.util.List;

@NativeQueryFolder("bookings/detail")
@Component
public interface BookingDetailNQ extends NativeQuery {

    BookingDetailTO bookingDetail(@NativeQueryParam(value = "stake") String stake,
                                  @NativeQueryParam(value = "bookingCode") String bookingCode,
                                  @NativeQueryParam(value = "skyuserId") Long skyuserId,
                                  @NativeQueryParam(value = "companyId") Long companyId,
                                  @NativeQueryParam(value = "role") String role,
                                  @NativeQueryParam(value = "locale") Long locale,
                                  @NativeQueryParam(value = "COMPLETED") String COMPLETED,
                                  @NativeQueryParam(value = "UPCOMING") String UPCOMING,
                                  @NativeQueryParam(value = "CANCELLED") String CANCELLED,
                                  @NativeQueryParam(value = "FAILED") String FAILED,
                                  @NativeQueryParam(value = "ONEWAY") String ONEWAY,
                                  @NativeQueryParam(value = "ROUND") String ROUND,
                                  @NativeQueryParam(value = "MULTICITY") String MULTICITY,
                                  @NativeQueryParam(value = "URLIMG_PROFILE") String URLIMG_PROFILE
                                );

    List<BaggageInfoTO> baggage(@NativeQueryParam(value = "bookingId") Integer bookingId);

    List<BaggageAllowanceTO> baggageAllowance(@NativeQueryParam(value = "baggageId") Integer baggageId);

    List<TicketInfoTO> ticket(@NativeQueryParam(value = "bookingId") Integer bookingId);

    List<PriceBreakDownTO> itineraryPrice(@NativeQueryParam(value = "bookingId") Integer bookingId);

    List<ItineraryODInfoTO> bookingOD(@NativeQueryParam(value = "bookingId") Integer bookingId);
    List<ItineraryODSegmentTO> bookingODSegment(@NativeQueryParam(value = "bodId") Integer bookingStopInfo,
                                                @NativeQueryParam(value = "IMAGE_URL") String IMAGE_URL,
                                                @NativeQueryParam(value = "localeId") Long localeId,
                                                @NativeQueryParam(value = "COMPLETED") String COMPLETED,
                                                @NativeQueryParam(value = "UPCOMING") String UPCOMING
                                                );

    List<ItineraryStopInfoTO> bookingStopInfo(@NativeQueryParam(value = "bodSegId") Integer bodSegId,
                                              @NativeQueryParam(value = "localeId") Long localeId
                                                    );

    BaggageLocationTO baggageLocation(@NativeQueryParam(value = "locationCode") String locationCode,
                                              @NativeQueryParam(value = "localeId") Long localeId
                                        );

}
