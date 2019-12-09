package com.skybooking.stakeholderservice.v1_0_0.util.skyowner;

import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyDocsEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyStatusEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderUserHasCompanyEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyStatusRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SkyownerRegisterRQ;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkyownerBean {


    @Autowired
    private ApiBean apiBean;

    @Autowired
    private UserBean userBean;

    @Autowired
    private CompanyStatusRP companyStatusRP;

    @Autowired
    private CompanyHasUserRP companyHasUserRP;

    @Autowired
    private GeneralBean generalBean;

    @Autowired
    private CompanyRP companyRP;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Create company
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyownerRQ;
     */
    public void addStakeHolderCompany(SkyownerRegisterRQ skyownerRQ, UserEntity user, UserRepository userRP) {

        List<StakeholderCompanyEntity> companies = new ArrayList<>();
        List<StakeHolderUserEntity> skyuser = new ArrayList<>();

        StakeholderCompanyEntity stkCompany = new StakeholderCompanyEntity();
        stkCompany.setTypeValue(skyownerRQ.getBusinessType());
        stkCompany.setCompanyName(skyownerRQ.getBusinessName());
        stkCompany.setSlug(apiBean.createSlug("companyTypes"));

        StakeholderCompanyEntity lastCode = companyRP.findByCompanyCode("SKYO");
        String companyCode = generalBean.stakeUniqueCode("SKYO", (lastCode != null) ? lastCode.getCompanyCode() : null, "02");
        stkCompany.setCompanyCode(companyCode);

        if (stkCompany.getTypeValue().equals("com_gov")) {
            stkCompany.setContactPerson(skyownerRQ.getContactPerson());
            stkCompany.setContactPosition(skyownerRQ.getContactPosition());
        }

        companies.add(stkCompany);
        skyuser.add(user.getStakeHolderUser());

        user.getStakeHolderUser().setStakeholderCompanies(companies);
        user.getStakeHolderUser().setIsSkyowner(1);

        for (StakeholderCompanyEntity stakeholderCompany: companies) {
            stakeholderCompany.setStakeHolderUsers(skyuser);
        }

        storeCompanyDocs(stkCompany, skyownerRQ);

        user = userRP.save(user);

        StakeholderCompanyEntity company = user.getStakeHolderUser().getStakeholderCompanies().stream().findFirst().get();

        apiBean.addSimpleContact(company.getId(), skyownerRQ.getUsername(), "e", "skyowner");
        apiBean.addSimpleContact(company.getId(),skyownerRQ.getCode() + "-" + skyownerRQ.getPhone().replaceFirst("^0+(?!$)", ""), "p", "skyowner");
        apiBean.addSimpleContact(company.getId(), "", "a", "skyowner");

        storeCompanyStatus(company.getId(), user.getId(), 0, "Waiting admin approve");
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Store company docs
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param stkCompany
     * @Param fileName
     * @Param fileName2
     */
    public void storeCompanyDocs(StakeholderCompanyEntity company, SkyownerRegisterRQ skyownerRQ) {

        List<StakeholderCompanyDocsEntity> companyDocs = new ArrayList<>();

        StakeholderCompanyDocsEntity companyDoc = new StakeholderCompanyDocsEntity();
        String nameFile = userBean.uploadLicense(skyownerRQ.getLicense(), "/company_license", null);
        companyDoc.setImage(nameFile);
        companyDocs.add(companyDoc);

        if (company.getTypeValue().equals("com_tra")) {
            StakeholderCompanyDocsEntity stkCompanyDocSecond = new StakeholderCompanyDocsEntity();
            String nameFile2 = userBean.uploadLicense(skyownerRQ.getLicenseSecond(), "/company_license", null);
            stkCompanyDocSecond.setImage(nameFile2);
            companyDocs.add(stkCompanyDocSecond);
        }

        for (StakeholderCompanyDocsEntity doc: companyDocs) {
            doc.setStakeholderCompany(company);
        }

        company.setStakeholderCompanyDocs(companyDocs);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Store company status
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param companyID
     * @Param userID
     * @Param status
     * @Param comment
     */
    public void storeCompanyStatus(Long companyID, Long userID, Integer status, String comment) {

        StakeholderCompanyStatusEntity companyStatus = new StakeholderCompanyStatusEntity();

        companyStatus.setActionableId(userID);
        companyStatus.setCompanyId(companyID);
        companyStatus.setStatus(status);
        companyStatus.setComment(comment);

        companyStatusRP.save(companyStatus);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Find a company
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param user
     * @Param id
     */
    public StakeholderCompanyEntity findCompany(UserEntity user, Long id) {

        Optional<StakeholderCompanyEntity> company = user.getStakeHolderUser().getStakeholderCompanies().stream().filter(p -> p.getId() == id).findFirst();

        if (company.isEmpty()) {
            throw new UnauthorizedException("sth_w_w", "");
        }

        return company.get();

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Find a company
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param companyId
     * @Param skyuserId
     */
    public void addStaff(Long companyId, Long skyuserId) {

        StakeholderUserHasCompanyEntity companyHasUser = new StakeholderUserHasCompanyEntity();
        companyHasUser.setStakeholderCompanyId(companyId);
        companyHasUser.setStakeholderUserId(skyuserId);
        companyHasUser.setStatus(2);

        companyHasUserRP.save(companyHasUser);

    }

}
