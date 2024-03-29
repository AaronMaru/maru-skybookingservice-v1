package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.user;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.stereotype.Component;

import java.util.List;

@NativeQueryFolder("user")
@Component
public interface UserNQ extends NativeQuery {

    List<PermissionTO> listPermission(@NativeQueryParam(value = "roles") String roles);

    TotalBookingTO totalBooking(@NativeQueryParam(value = "skyuserId") Long skyuserId,
                                @NativeQueryParam(value = "companyId") Long companyId,
                                @NativeQueryParam(value = "stake") String stake);

    NationalityTO getNationality(@NativeQueryParam(value = "isoCountry") String isoCountry,
                                 @NativeQueryParam(value = "localId") Long localId);

    UserReferenceTO stakeholderInfo(@NativeQueryParam(value = "skyuserId") Long skyuserId);
    UserReferenceTO companyInfo(@NativeQueryParam(value = "skyuserId") Long skyuserId,
                                @NativeQueryParam(value = "companyId") Long companyId);

    PaymentUserInfoTO paymentUserInfo(@NativeQueryParam(value = "skyuserId") Long skyuserId);
}
