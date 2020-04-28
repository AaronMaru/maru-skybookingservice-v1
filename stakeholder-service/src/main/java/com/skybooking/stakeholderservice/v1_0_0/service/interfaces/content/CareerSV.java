package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.content;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.CareersDetailRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.CareersRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CareerSV {

    List<CareersRS> getCareeries();
    CareersDetailRS getCareerDetail(Long id);

}
