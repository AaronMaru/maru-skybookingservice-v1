package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.company;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.CompanyRS;
import org.springframework.stereotype.Service;

@Service
public interface CompanySV {
    CompanyRS updateCompany(CompanyRQ companyRQ, Long id);
}
