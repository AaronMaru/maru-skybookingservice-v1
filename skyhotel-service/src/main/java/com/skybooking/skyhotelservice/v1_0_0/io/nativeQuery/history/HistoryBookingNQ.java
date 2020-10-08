package com.skybooking.skyhotelservice.v1_0_0.io.nativeQuery.history;

import io.github.gasparbarancelli.NativeQuery;
import io.github.gasparbarancelli.NativeQueryFolder;
import io.github.gasparbarancelli.NativeQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@NativeQueryFolder("history")
@Component
public interface HistoryBookingNQ extends NativeQuery {

    @Transactional
    Page<HistoryBookingTO> historyBooking(@NativeQueryParam(value = "companyId") Long companyId,
                                          @NativeQueryParam(value = "skyuserId") Long skyuserId,
                                          @NativeQueryParam(value = "skyType") String skyType,
                                          Pageable pageable);

    Optional<HistoryBookingTO> historyBookingDetail(@NativeQueryParam(value = "companyId") Long companyId,
                                                    @NativeQueryParam(value = "skyuserId") Long skyuserId,
                                                    @NativeQueryParam(value = "bookingCode") String bookingCode,
                                                    @NativeQueryParam(value = "skyType") String skyType);

}
