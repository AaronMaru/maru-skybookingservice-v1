package com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@NativeQueryFolder("staff")
@Component
public interface StaffNQ extends NativeQuery {

    List<SkyuserSearchTO> listSkyuserByEmailOrPhone(@NativeQueryParam(value = "keyword") String keyword);

    List<PendingStaffEmailTO> listPendingEmailStaff(@NativeQueryParam(value = "companyId") Long companyId);

    Page<StaffTO> listStaff(@NativeQueryParam(value = "companyId") Long companyId,
                            @NativeQueryParam(value = "keyword") String keyword,
                            @NativeQueryParam(value = "role") String role,
                            @NativeQueryParam(value = "startPRange") Integer startPRange,
                            @NativeQueryParam(value = "endPRange") Integer endPRange,
                            @NativeQueryParam(value = "joinDate") String joinDate,
                            @NativeQueryParam(value = "joinStatus") String joinStatus,
                            @NativeQueryParam(value = "action") String action,
                            @NativeQueryParam(value = "ACTIVE") String ACTIVE,
                            @NativeQueryParam(value = "INACTIVE") String INACTIVE,
                            @NativeQueryParam(value = "BANNED") String BANNED,
                            @NativeQueryParam(value = "STAFF_PROFILE") String STAFF_PROFILE,
                            @NativeQueryParam(value = "sortField") String sortField,
                            @NativeQueryParam(value = "orderBy") String orderBy,
                            Pageable pageable);

    List<StaffTotalBookingTO> listStaffTotalBooking(@NativeQueryParam(value = "skyuserId") Integer skyuserId,
                                                    @NativeQueryParam(value = "companyId") Long companyId,
                                                    @NativeQueryParam(value = "COMPLETED") String COMPLETED,
                                                    @NativeQueryParam(value = "UPCOMING") String UPCOMING,
                                                    @NativeQueryParam(value = "CANCELLED") String CANCELLED,
                                                    @NativeQueryParam(value = "FAILED") String FAILED
                                                    );

    List<RoleTO> listOrFindRole(@NativeQueryParam(value = "action") String action, @NativeQueryParam(value = "role") String role);

    List<RolePermissionTO> findPermissionRole(@NativeQueryParam(value = "role") String role, @NativeQueryParam(value = "permission") String permission);

    List<StaffProfileTO> findProfileStaff(@NativeQueryParam(value = "skyuserId") Long skyuserId,
                                          @NativeQueryParam(value = "companyId") Long companyId,
                                          @NativeQueryParam(value = "ACTIVE") String ACTIVE,
                                          @NativeQueryParam(value = "INACTIVE") String INACTIVE,
                                          @NativeQueryParam(value = "BANNED") String BANNED
                                       );

    SkyuserTO findSkyuser(@NativeQueryParam(value = "skyuserId") Long skyuserId, @NativeQueryParam(value = "STAFF_PROFILE") String STAFF_PROFILE);


}
