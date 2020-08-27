package com.skybooking.skyhotelservice.v1_0_0.io.nativeQuery.shopping;

import io.github.gasparbarancelli.NativeQuery;
import io.github.gasparbarancelli.NativeQueryFolder;
import io.github.gasparbarancelli.NativeQueryParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@NativeQueryFolder("shopping")
@Component
public interface ShoppingNQ extends NativeQuery {

    @Transactional
    HotelMarkupTO hotelMarkup(@NativeQueryParam(value = "hotelPrice") BigDecimal hotelPrice,
                              @NativeQueryParam(value = "skyType") String skyType);

}
