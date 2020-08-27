package com.skybooking.eventservice.v1_0_0.io.nativeQuery.notification;

import io.github.gasparbarancelli.NativeQuery;
import io.github.gasparbarancelli.NativeQueryFolder;
import io.github.gasparbarancelli.NativeQueryParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NativeQueryFolder("notification")
@Component
public interface NotificationNQ extends NativeQuery {

    @Transactional
    ScriptingTO scripting(@NativeQueryParam(value = "locale") Long locale,
                          @NativeQueryParam(value = "urlKey") String urlKey);

    @Transactional
    List<String> getPlayerIdByStakeholderUserId(@NativeQueryParam(value = "stakeholderUserId") Integer stakeholderUserId);
}
