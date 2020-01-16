package com.skybooking.staffservice.v1_0_0.io.nativeQuery.mail;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.stereotype.Component;

@NativeQueryFolder("mail")
@Component
public interface MultiLanguageNQ extends NativeQuery {


    MultiLanguageTO mailMultiLanguage(@NativeQueryParam(value = "locale") long locale, @NativeQueryParam(value = "keyword") String keyword);

    MailScriptLocaleTO mailScriptLocale(@NativeQueryParam(value = "locale") long locale, @NativeQueryParam(value = "keyword") String keyword);
}
