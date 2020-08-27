package com.skybooking.eventservice.v1_0_0.io.nativeQuery.mail;

import io.github.gasparbarancelli.NativeQuery;
import io.github.gasparbarancelli.NativeQueryFolder;
import io.github.gasparbarancelli.NativeQueryParam;
import org.springframework.stereotype.Component;

@NativeQueryFolder("mail")
@Component
public interface MultiLanguageNQ extends NativeQuery {

    MultiLanguageTO mailMultiLanguage(@NativeQueryParam(value = "locale") long locale, @NativeQueryParam(value = "keyword") String keyword);

    MailScriptLocaleTO mailScriptLocale(@NativeQueryParam(value = "locale") long locale, @NativeQueryParam(value = "keyword") String keyword);

}
