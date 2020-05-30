package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.passenger;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.passenger.PassengerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Created by : maru
 * Date  : 3/5/2020
 * Time  : 9:38 AM
 */
@NativeQueryFolder("passenger")
@Component
public interface PassengerNQ extends NativeQuery {

    Page<PassengerTO> getPassenger(@NativeQueryParam(value = "skyuserId") Long skyuserId,
                                   @NativeQueryParam(value = "companyId") Long companyId,
                                   @NativeQueryParam(value = "role") String role,
                                   @NativeQueryParam(value = "passType") String passType,
                                   @NativeQueryParam(value = "stake") String stake,
                                   @NativeQueryParam(value = "localeId") long localeId,
                                   Pageable pageable);

    PassengerTO getPassengerById(@NativeQueryParam(value = "id") Long id,
                                 @NativeQueryParam(value = "skyuserId") Long skyuserId,
                                 @NativeQueryParam(value = "companyId") Long companyId,
                                 @NativeQueryParam(value = "role") String role,
                                 @NativeQueryParam(value = "stake") String stake,
                                 @NativeQueryParam(value = "localeId") long localeId);

}
