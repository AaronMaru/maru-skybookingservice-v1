package com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.notification;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@NativeQueryFolder("notification")
@Component
public interface NotificationNQ extends NativeQuery {

    @Transactional
    ScriptingTO scripting(@NativeQueryParam(value = "locale") Long locale,
                          @NativeQueryParam(value = "urlKey") String urlKey);

    @Transactional
    String getPlayerIdByStakeholderUserId(@NativeQueryParam(value = "stakeholderUserId") Integer stakeholderUserId);
}
