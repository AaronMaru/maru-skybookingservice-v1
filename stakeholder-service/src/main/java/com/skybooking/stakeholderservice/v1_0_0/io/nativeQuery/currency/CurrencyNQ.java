package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.currency;

import com.skybooking.library.NativeQuery;
import com.skybooking.library.NativeQueryFolder;
import com.skybooking.library.NativeQueryParam;
import org.springframework.stereotype.Component;
import java.util.List;

@NativeQueryFolder("currency")
@Component
public interface CurrencyNQ extends NativeQuery {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get all currencies by locale's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @return List
     */
    List<CurrencyTO> findAllByLocaleId(@NativeQueryParam(value = "id") long id);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get currency by id and locale's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @param locale
     * @return
     */
    CurrencyTO findAllByIdAndLocale(@NativeQueryParam(value = "id") long id, @NativeQueryParam(value = "locale") long locale);

}
