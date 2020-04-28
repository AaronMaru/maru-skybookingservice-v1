package com.skybooking.stakeholderservice.v1_0_0.ui.controller.app.content;


import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.content.PageSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.BannerRS;
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
@RequestMapping("/mv1.0.0/utils")
public class ContentControllerM {

    @Autowired
    private PageSV pageSV;

    @Autowired
    private LocalizationBean localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get a listing pages
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResRS
     */
    @GetMapping("/pages")
    public ResRS getPages () {
        List<PagesRS> data = pageSV.getPages();
        return localization.resAPI(HttpStatus.OK,"res_succ", data);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get a page
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResRS
     */
    @GetMapping("/pages/{code}")
    public ResRS getByPage (@PathVariable String code) {
        PagesRS data = pageSV.getByPage(code);
        return localization.resAPI(HttpStatus.OK,"res_succ", data);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get a banner
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return ResRS
     */
    @GetMapping("/banner")
    public ResRS getByPage () {
        BannerRS data = pageSV.getBanners("mobile");
        return localization.resAPI(HttpStatus.OK,"res_succ", data);
    }

}
