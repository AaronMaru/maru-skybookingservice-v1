package com.skybooking.backofficeservice.v1_0_0.io.nativeQuery.popularCity;

import io.github.gasparbarancelli.NativeQuery;
import io.github.gasparbarancelli.NativeQueryFolder;
import org.springframework.stereotype.Component;

import java.util.List;

@NativeQueryFolder("hotelPopularCity")
@Component
public interface PopularCityNQ extends NativeQuery {

    List<PopularCityTO> getPopularCity();
}
