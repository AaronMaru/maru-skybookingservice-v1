package com.skybooking.stakeholderservice.v1_0_0.transformer.locale;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.locale.LocaleEntity;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.locale.LocaleRS;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class LocaleTF {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response from entity
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param entity
     * @return LocaleRS
     */
    public static LocaleRS getResponse(LocaleEntity entity) {

        LocaleRS response = new LocaleRS();
        BeanUtils.copyProperties(entity, response);

        return response;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get response list from entities
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param entities
     * @return List
     */
    public static List<LocaleRS> getResponseList(List<LocaleEntity> entities) {

        List<LocaleRS> responses = new ArrayList<>();
        for (LocaleEntity entity : entities) {
            responses.add(getResponse(entity));
        }

        return responses;
    }
}
