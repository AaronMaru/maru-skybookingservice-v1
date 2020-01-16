package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.company;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.BussinessDocRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.BussinessTypeRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.CompanyRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanySV {
    CompanyRS updateCompany(CompanyRQ companyRQ, Long id);
    List<BussinessTypeRS> bussinessTypes();
    List<BussinessDocRS> bussinessDoc(Long id);
}
