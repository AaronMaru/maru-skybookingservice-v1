package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.setting;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@NativeQueryFolder("setting")
@Component
public interface SettingNotificationNQ extends NativeQuery {

    @Transactional
    void updateEmailNotificationSetting(@NativeQueryParam("stakeholder") Integer stakeholder,
                                        @NativeQueryParam("company") Integer company,
                                        @NativeQueryParam("enabled") Boolean enabled);

    @Transactional
    void updatePushNotificationSetting(@NativeQueryParam("stakeholder") Integer stakeholder,
                                       @NativeQueryParam("company") Integer company,
                                       @NativeQueryParam("enabled") Boolean enabled);

    @Transactional
    SettingNotificationTO getNotificationSetting(@NativeQueryParam("stakeholder") Integer stakeholder,
                                                 @NativeQueryParam("company") Integer company);

}
