package com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.flight;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import org.springframework.stereotype.Component;

import java.util.List;

@NativeQueryFolder("flight")
@Component
public interface FlightInfoNQ extends NativeQuery {

    List<FlightInfoTO> getFlightInfoShoppingEnabled();
}
