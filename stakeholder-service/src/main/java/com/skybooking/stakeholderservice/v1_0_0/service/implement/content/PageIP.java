package com.skybooking.stakeholderservice.v1_0_0.service.implement.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skybooking.stakeholderservice.constant.AwsPartConstant;
import com.skybooking.stakeholderservice.exception.httpstatus.NotFoundException;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.content.page.FrontConfigRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.content.page.FrontendPageLocaleRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.content.page.FrontendPageRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.content.PageSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.BannerRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.PagesRS;
import com.skybooking.stakeholderservice.v1_0_0.util.general.AwsPartBean;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class PageIP implements PageSV {

    @Autowired
    private FrontendPageRP frontendPageRP;

    @Autowired
    private FrontendPageLocaleRP frontendPageLocaleRP;

    @Autowired
    private FrontConfigRP frontConfigRP;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private AwsPartBean awsPartBean;

    @Autowired
    Environment environment;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get a listing pages
     * -----------------------------------------------------------------------------------------------------------------
     *
     * List<PagesRS>
     */
    public List<PagesRS> getPages() {

        var pages = frontendPageRP.getAllByAllowPublic(1);
        List<PagesRS> pagesRS = new ArrayList<>();

        pages.forEach(data -> {
            var pageLocale = frontendPageLocaleRP.getByPageIdAndLocale(data.getId(), headerBean.getLocalization());
            if (pageLocale == null) {
                pageLocale = frontendPageLocaleRP.getByPageIdAndLocale(data.getId(), "en");
            }

            PagesRS pageRS = new PagesRS();
            pageRS.setTitle(pageLocale.getTitle());

            var mapper = new ObjectMapper();
            try {
                var dataJson = mapper.readTree(pageLocale.getBody());

                if (dataJson.has("top_banner")) {
                    var dataJsonAbout = (ObjectNode) mapper.readTree(pageLocale.getBody());
                    var bannerUrl =  dataJson.get("top_banner").textValue();
                    dataJsonAbout.put("top_banner", bannerUrl);
                    pageRS.setBody(dataJsonAbout);
                }

                pageRS.setBody(dataJson);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            pageRS.setMetaTitle(data.getMetaTitle());
            pageRS.setMetaDescription(data.getMetaDescription());
            pageRS.setMetaKeyword(data.getMetaKeyword());
            pageRS.setCode(data.getCode());
            pageRS.setAwsUrl(awsPartBean.partUrl(AwsPartConstant.CONTENT, null));

            pagesRS.add(pageRS);

        });

        return pagesRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get a page
     * -----------------------------------------------------------------------------------------------------------------
     *
     * List<PagesRS>
     */
    public PagesRS getByPage(String code) {

        List<PagesRS> data = getPages();
        var page = data.stream().filter(item -> item.getCode().equals(code)).findFirst();

        if (page.isEmpty()) {
            throw new NotFoundException("url_not_found", null);
        }

        return page.get();

    }



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get banner
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return BannerRS
     */
    public BannerRS getBanners(String app) {

        BannerRS bannersRS = new BannerRS();

        if (app.equals("web")) {
            var banner = frontConfigRP.findByName("web_site_banner");
            bannersRS.setBanner(setBannerUrl(banner.getTextValue()));
        }

        if (app.equals("mobile")) {
            var banner = frontConfigRP.findByName("mobile_main_banner");
            var subBanner = frontConfigRP.findByName("mobile_sub_banner");
            bannersRS.setBanner(setBannerUrl(banner.getTextValue()));
            bannersRS.setSubBanner(setBannerUrl(subBanner.getTextValue()));
        }

        return bannersRS;

    }

    private String setBannerUrl(String banner) {
        String bannerUrl = environment.getProperty("spring.awsImageUrl.banner") + banner;
        return bannerUrl;
    }

}
