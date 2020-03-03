package com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.stereotype.Component;

@NativeQueryFolder("shopping")
@Component
public interface MarkupNQ extends NativeQuery {

    MarkupTO getMarkupPriceAnonymousUser(@NativeQueryParam("cabinClass") String cabin, @NativeQueryParam("userType") String userType);

    MarkupTO getMarkupPriceSkyUser(@NativeQueryParam("userId") Integer userId, @NativeQueryParam("cabinClass") String cabin);

    MarkupTO getMarkupPriceSkyOwnerUser(@NativeQueryParam("companyId") Integer companyId, @NativeQueryParam("cabinClass") String cabin);

    MarkupTO getGeneralMarkupPayment();

}
