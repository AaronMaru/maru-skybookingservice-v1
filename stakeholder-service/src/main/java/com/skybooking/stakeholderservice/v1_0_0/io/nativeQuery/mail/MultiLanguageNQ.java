package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.mail;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@NativeQueryFolder("mail")
@Component
public interface MultiLanguageNQ extends NativeQuery {

    @Transactional
    MultiLanguageTO mailMultiLanguage(@NativeQueryParam(value = "locale") long locale, @NativeQueryParam(value = "keyword") String keyword);

    @Transactional
    MailScriptLocaleTO mailScriptLocale(@NativeQueryParam(value = "locale") long locale, @NativeQueryParam(value = "keyword") String keyword);

}
