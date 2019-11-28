package com.skybooking.staffservice.v1_0_0.util;

import com.skybooking.staffservice.v1_0_0.io.enitity.company.StakeholderUserHasCompanyEntity;
import com.skybooking.staffservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import org.springframework.beans.factory.annotation.Autowired;

public class GeneralBean {


    @Autowired
    private CompanyHasUserRP companyHasUserRP;



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Find a company
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param companyId
     * @Param skyuserId
     */
    public void addStaff(Long companyId, Long skyuserId, String role) {

        StakeholderUserHasCompanyEntity companyHasUser = new StakeholderUserHasCompanyEntity();
        companyHasUser.setStakeholderCompanyId(companyId);
        companyHasUser.setStakeholderUserId(skyuserId);
        companyHasUser.setSkyuserRole(role);
        companyHasUser.setStatus(2);

        companyHasUserRP.save(companyHasUser);

    }

}
