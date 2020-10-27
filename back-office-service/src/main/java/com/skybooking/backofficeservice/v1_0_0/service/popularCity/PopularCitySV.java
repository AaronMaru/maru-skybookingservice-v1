package com.skybooking.backofficeservice.v1_0_0.service.popularCity;

import com.skybooking.backofficeservice.v1_0_0.ui.model.request.quick.QuickRQ;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.PopularCityRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PopularCitySV {

    List<PopularCityRS> listing();

    void created(QuickRQ quickRQ);

    Boolean deleted(Integer id);

}
