package com.skybooking.stakeholderservice.v1_0_0.ui.controller.common.content;


import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.content.HelpCenterSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.CategoryRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.QuestionRS;
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
public class HelpCenterController {

    @Autowired
    private HelpCenterSV helpCenterSV;

    @Autowired
    private LocalizationBean localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get a listing categories
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResRS
     */
    @GetMapping("/categories")
    public ResRS getCareeries () {
       List<CategoryRS> data = helpCenterSV.getCategories();
        return localization.resAPI(HttpStatus.OK,"res_succ", data);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get a listing question
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResRS
     */
    @GetMapping("/questions/{catId}")
    public ResRS getQuestions (@PathVariable Long catId) {
        List<QuestionRS> data = helpCenterSV.getQuestions(catId);
        return localization.resAPI(HttpStatus.OK,"res_succ", data);
    }

}
