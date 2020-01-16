package com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@NativeQueryFolder("shopping")
@Component
public interface AircraftNQ extends NativeQuery {

    @Transactional
    AircraftTO getAircraftInformation(@NativeQueryParam("aircraft") String aircraft);

}
