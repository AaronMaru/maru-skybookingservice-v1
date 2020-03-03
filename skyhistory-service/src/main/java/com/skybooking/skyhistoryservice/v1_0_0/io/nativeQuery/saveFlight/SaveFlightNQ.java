package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.saveFlight;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@NativeQueryFolder("saveFlight")
@Component
public interface SaveFlightNQ extends NativeQuery {

    Page<SaveFlightTO> savedFlight(@NativeQueryParam(value = "skyuserId") Long skyuserId,
                                   @NativeQueryParam(value = "companyId") Long companyId,
                                   @NativeQueryParam(value = "action") String action,
                                   @NativeQueryParam(value = "keyword") String keyword,
                                   @NativeQueryParam(value = "role") String role,
                                   @NativeQueryParam(value = "stake") String stake,
                                   Pageable pageable);

    List<SaveFlightODTO> saveFlightSegments(@NativeQueryParam(value = "sFlightId") Long sFlightId);

}
