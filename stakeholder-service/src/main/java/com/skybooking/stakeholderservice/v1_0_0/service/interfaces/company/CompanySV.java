package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.company;

import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.company.CompanyDetailsTO;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.company.CompanyTO;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.info.CompanyUserDetailsTO;
import com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.info.CompanyUserTO;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyUpdateRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyUserRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.BussinessDocRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.BussinessTypeRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.CompanyRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanySV {
    CompanyRS updateCompany(CompanyUpdateRQ companyRQ, Long id);
    List<BussinessTypeRS> bussinessTypes();
    List<BussinessDocRS> bussinessDoc(Long id);
    List<CompanyTO> listCompany(String keyword);
    CompanyDetailsTO companyDetail(String companyCode);
    List<CompanyUserTO> listCompanyUser(String keyword);
    CompanyUserDetailsTO companyOrUserDetails(String companyCode);
    List<CompanyUserTO> companyOrUserList(CompanyUserRQ companyUserRQ);
}
