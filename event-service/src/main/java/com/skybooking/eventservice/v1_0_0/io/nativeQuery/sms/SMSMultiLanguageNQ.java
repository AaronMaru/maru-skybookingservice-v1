package com.skybooking.eventservice.v1_0_0.io.nativeQuery.sms;

import io.github.gasparbarancelli.NativeQuery;
import io.github.gasparbarancelli.NativeQueryFolder;
import io.github.gasparbarancelli.NativeQueryParam;
import org.springframework.stereotype.Component;

@NativeQueryFolder("sms")
@Component
public interface SMSMultiLanguageNQ extends NativeQuery {

    SMSMultiLanguageTO smsMultiLanguage(@NativeQueryParam(value = "locale") long locale, @NativeQueryParam(value = "keyword") String keyword);

}
