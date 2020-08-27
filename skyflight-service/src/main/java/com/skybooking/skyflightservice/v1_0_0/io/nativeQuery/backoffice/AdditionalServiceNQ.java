package com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.backoffice;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.stereotype.Component;

import java.util.List;

@NativeQueryFolder("backoffice")
@Component
public interface AdditionalServiceNQ extends NativeQuery {
    List<AdditionalServiceTO> getAdditionalServices(@NativeQueryParam(value = "bookingId") Integer bookingId);
}
