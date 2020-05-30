package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@NativeQueryFolder("notification")
@Component
public interface NotificationNQ extends NativeQuery {

    List<StakeholderUserPlayerTO> stakeholderPlayerId(@NativeQueryParam(value = "skyuserId") Long skyuserId);

    List<CompanyPlayerTO> companyPlayerId(@NativeQueryParam(value = "companyId") Long companyId,
                                          @NativeQueryParam(value = "role") String role);

    List<CompanyByRolePlayerTO> companyByRolePlayerId(@NativeQueryParam(value = "role") String role,
                                                      @NativeQueryParam(value = "companyId") Long companyId,
                                                      @NativeQueryParam(value = "skyuserId") Long skyuserId);

    Page<NotificationTO> listNotification(@NativeQueryParam(value = "stake") String stake,
                                          @NativeQueryParam(value = "locale") Long locale,
                                          @NativeQueryParam(value = "companyId") Long companyId,
                                          @NativeQueryParam(value = "skyuserId") Long skyuserId,
                                          @NativeQueryParam(value = "role") String role,
                                          Pageable pageable);

    NotificationDetailTO notificationDetail(@NativeQueryParam(value = "locale") Long locale,
                                            @NativeQueryParam(value = "notiId") Long notiId);

    List<NotificationBookingTO> notificationFlightBooking( @NativeQueryParam(value = "locale") Long locale,
                                                           @NativeQueryParam(value = "bookingId") Integer bookingId );
    @Transactional
    ScriptingTO scripting(@NativeQueryParam(value = "locale") Long locale,
                          @NativeQueryParam(value = "urlKey") String urlKey);



}
