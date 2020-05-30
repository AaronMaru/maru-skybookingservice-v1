package com.skybooking.stakeholderservice.v1_0_0.service.implement.content;

import com.skybooking.stakeholderservice.exception.httpstatus.NotFoundException;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.content.career.CareerLocaleRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.content.career.CareerRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.content.CareerSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.CareersDetailRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.CareersRS;
import com.skybooking.stakeholderservice.v1_0_0.util.datetime.DateTimeBean;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class CareerIP implements CareerSV {

    @Autowired
    private CareerRP careerRP;

    @Autowired
    private CareerLocaleRP careerLocaleRP;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private DateTimeBean dateTimeBean;


    public List<CareersRS> getCareeries() {

        var careeries = careerRP.findByStatus(1);

        List<CareersRS> careersRS = new ArrayList<>();

        careeries.forEach(data -> {
            var careerLocale = careerLocaleRP.findByCareerIdAndLocale(data.getId(), headerBean.getLocalization());
            if (careerLocale == null) {
                careerLocale = careerLocaleRP.findByCareerIdAndLocale(data.getId(), "en");
            }

            CareersRS careerRS = new CareersRS();

            careerRS.setId(data.getId());
            careerRS.setTitle(careerLocale.getTitle());
            careerRS.setExpiredDate(dateTimeBean.convertDateTimeISO(data.getExpiredDate()));
            careerRS.setShift(data.getShift());

            careersRS.add(careerRS);

        });

        return careersRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get a career detail
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return CareersDetailRS
     */
    public CareersDetailRS getCareerDetail(Long id) {

        var career = careerRP.findByIdAndStatus(id, 1);

        if (career.isEmpty()) {
            throw new NotFoundException("url_not_found", null);
        }

        CareersDetailRS careersDetailRS = new CareersDetailRS();

        var careerLocale = careerLocaleRP.findByCareerIdAndLocale(career.get().getId(), headerBean.getLocalization());
        if (careerLocale == null) {
            careerLocale = careerLocaleRP.findByCareerIdAndLocale(career.get().getId(), "en");
        }

        careersDetailRS.setTitle(careerLocale.getTitle());
        careersDetailRS.setStartDate(dateTimeBean.convertDateTimeISO(career.get().getValidDate()));
        careersDetailRS.setExpiredDate(dateTimeBean.convertDateTimeISO(career.get().getExpiredDate()));
        careersDetailRS.setTags(career.get().getTags());
        careersDetailRS.setHiring(career.get().getHiring());
        careersDetailRS.setDescription(careerLocale.getDescription());
        careersDetailRS.setRequirement(careerLocale.getRequirement());
        careersDetailRS.setContact(careerLocale.getContact());
        careersDetailRS.setResponsibility(careerLocale.getResponsibility());

        return careersDetailRS;

    }

}
