package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.content;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.BannerRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.PagesRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PageSV {

    List<PagesRS> getPages();
    PagesRS getByPage(String code);
    BannerRS getBanners(String app);

}
