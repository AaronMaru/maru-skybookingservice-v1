package com.skybooking.stakeholderservice.v1_0_0.ui.controller.common.content;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.content.CareerSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.CareersDetailRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.CareersRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.PagesRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1.0.0/utils")
public class CareerController {

    @Autowired
    private CareerSV careerSV;

    @Autowired
    private LocalizationBean localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get a listing pages
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResRS
     */
    @GetMapping("/career")
    public ResRS getCareeries () {
        List<CareersRS> data = careerSV.getCareeries();
        return localization.resAPI(HttpStatus.OK,"res_succ", data);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get a listing pages
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResRS
     */
    @GetMapping("/career/{id}")
    public ResRS getCareerDetails (@PathVariable Long id) {
        CareersDetailRS data = careerSV.getCareerDetail(id);
        return localization.resAPI(HttpStatus.OK,"res_succ", data);
    }

}
