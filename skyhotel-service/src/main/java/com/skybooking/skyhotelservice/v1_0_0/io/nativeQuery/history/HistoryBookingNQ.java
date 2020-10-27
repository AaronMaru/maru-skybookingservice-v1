package com.skybooking.skyhotelservice.v1_0_0.io.nativeQuery.history;

import io.github.gasparbarancelli.NativeQuery;
import io.github.gasparbarancelli.NativeQueryFolder;
import io.github.gasparbarancelli.NativeQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@NativeQueryFolder("history")
@Component
public interface HistoryBookingNQ extends NativeQuery {

    @Transactional
    List<HistoryBookingTO> historyBooking(@NativeQueryParam(value = "companyId") Long companyId,
                                          @NativeQueryParam(value = "skyuserId") Long skyuserId,
                                          @NativeQueryParam(value = "keywordSearch") String keywordSearch,
                                          @NativeQueryParam(value = "bookedBy") String bookedBy,
                                          @NativeQueryParam(value = "checkIn") String checkIn,
                                          @NativeQueryParam(value = "checkOut") String checkOut,
                                          @NativeQueryParam(value = "roomNumber") Integer roomNumber,
                                          @NativeQueryParam(value = "bookingStatus") String bookingStatus,
                                          @NativeQueryParam(value = "paymentType") String paymentType,
                                          @NativeQueryParam(value = "priceRangeStart") Integer priceRangeStart,
                                          @NativeQueryParam(value = "priceRangeEnd") Integer priceRangeEnd,
                                          @NativeQueryParam(value = "action") String action,
                                          @NativeQueryParam(value = "defaultStatus") List<String> defaultStatus,
                                          @NativeQueryParam(value = "skyType") String skyType);

    @Transactional
    Optional<HistoryBookingTO> historyBookingDetail(@NativeQueryParam(value = "companyId") Long companyId,
                                                    @NativeQueryParam(value = "skyuserId") Long skyuserId,
                                                    @NativeQueryParam(value = "bookingCode") String bookingCode,
                                                    @NativeQueryParam(value = "skyType") String skyType);

}
