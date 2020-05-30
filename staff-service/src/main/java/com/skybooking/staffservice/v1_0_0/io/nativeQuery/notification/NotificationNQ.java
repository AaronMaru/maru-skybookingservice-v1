package com.skybooking.staffservice.v1_0_0.io.nativeQuery.notification;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
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
    @Transactional
    ScriptingTO scripting(@NativeQueryParam(value = "locale") Long locale,
                          @NativeQueryParam(value = "urlKey") String urlKey);

}
