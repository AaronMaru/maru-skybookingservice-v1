package com.skybooking.stakeholderservice.exception.anotation;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyRP;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CompanyNameValidator implements ConstraintValidator<CompanyName, String> {

    @Autowired
    private CompanyRP companyRP;

    @Autowired
    private UserBean userBean;

    @Autowired
    private HttpServletRequest request;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        int index = request.getServletPath().lastIndexOf('/');
        String seg = request.getServletPath().substring(0,index);
        String lastSeg = seg.substring(seg.lastIndexOf('/') + 1);

        boolean b = true;

        StakeholderCompanyEntity stkCompany = companyRP.findFirstByCompanyName(value, lastSeg.equals("company") ? userBean.getUserPrincipalVld().getStakeHolderUser().getStakeholderCompanies().stream().findFirst().get().getId() : null);

        if (stkCompany != null) {
            b = false;
        }

        return b;

    }
}
