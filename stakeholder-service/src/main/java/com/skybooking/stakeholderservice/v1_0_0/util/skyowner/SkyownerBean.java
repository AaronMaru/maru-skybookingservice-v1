package com.skybooking.stakeholderservice.v1_0_0.util.skyowner;

import com.skybooking.stakeholderservice.constant.BussinessConstant;
import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.*;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeholderUserInvitationEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.*;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SkyownerRegisterRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.CompanyRS;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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

    @Autowired
    private BussinessDocRP bussinessDocRP;

    @Autowired
    private BussinessTypeRP bussinessTypeRP;

    @Autowired
    private BussinessTypeLocaleRP bussinessTypeLocaleRP;

    @Autowired
    private BussinessDocLocaleRP bussinessDocLocaleRP;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Create company
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyownerRQ;
     */
    public List<CompanyRS> addStakeHolderCompany(SkyownerRegisterRQ skyownerRQ, UserEntity user, UserRepository userRP) {

        List<StakeholderCompanyEntity> companies = new ArrayList<>();
        List<StakeHolderUserEntity> skyuser = new ArrayList<>();

        StakeholderCompanyEntity stkCompany = new StakeholderCompanyEntity();
        stkCompany.setBussinessTypeId(skyownerRQ.getBusinessTypeId());
        stkCompany.setCompanyName(skyownerRQ.getBusinessName());
        stkCompany.setSlug(apiBean.createSlug("companyTypes"));

        StakeholderCompanyEntity lastCode = companyRP.findByCompanyCode("SKYO");
        String companyCode = generalBean.stakeUniqueCode("SKYO", (lastCode != null) ? lastCode.getCompanyCode() : null, "2");
        stkCompany.setCompanyCode(companyCode);

        var bizTypeLocale = bussinessTypeLocaleRP.findByBusinessTypeIdAndLocaleId(skyownerRQ.getBusinessTypeId(), (long) 1);
        if (bizTypeLocale.getName().equalsIgnoreCase(BussinessConstant.GOVERMENT)) {
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

        try {
            storeCompanyDocs(stkCompany, skyownerRQ, "add");
            user = userRP.save(user);
        } catch (Exception e) {
            throw e;
        }

        StakeholderCompanyEntity company = user.getStakeHolderUser().getStakeholderCompanies().stream().findFirst().get();
        apiBean.storeOrUpdateCountry(skyownerRQ.getCountryId(), "company", company.getId());
        storeContacts(skyownerRQ, company.getId());

        storeCompanyStatus(company.getId(), user.getId(), 0, "Waiting admin approve");

        List<StakeholderCompanyEntity> bussinees = new ArrayList<>();
        bussinees.add(company);

        List<CompanyRS> companyRS = userBean.companiesDetails(bussinees);

        return companyRS;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Store contacts
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param skyownerRQ
     * @Param id
     */
    public void storeContacts(SkyownerRegisterRQ skyownerRQ, Long id) {
        apiBean.addSimpleContact(id,skyownerRQ.getCode() + "-" + skyownerRQ.getPhone().replaceFirst("^0+(?!$)", ""), "p", "company");
        apiBean.addSimpleContact(id, skyownerRQ.getWebsite(), "w", "company");
        apiBean.addSimpleContact(id, skyownerRQ.getPostalOrZipCode(), "z", "company");
        apiBean.addSimpleContact(id, skyownerRQ.getEmail(), "e", "company");
        apiBean.addSimpleContact(id, skyownerRQ.getAddress(), "a", "company");
        apiBean.addSimpleContact(id, skyownerRQ.getCity(), "c", "company");
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Store company docs
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param company
     * @Param skyownerRQ
     */
    public void storeCompanyDocs(StakeholderCompanyEntity company, SkyownerRegisterRQ skyownerRQ, String action) {

        Set<StakeholderCompanyDocsEntity> companyDocs = new HashSet<>();

        skyownerRQ.getLicenses().forEach((key, file)->{

            StakeholderCompanyDocsEntity companyDoc = new StakeholderCompanyDocsEntity();

            BussinessDocEntity bizDoc = bussinessDocRP.findFirstById(key);
            String nameFile = userBean.uploadLicense(file, "/company_license", null);

            companyDoc.setFileName(nameFile);
            companyDoc.setIsRequired(bizDoc.getIsRequired());

            companyDocs.add(companyDoc);

            companyDoc.setStakeholderCompany(company);
            storeCompanyDocsLocale(companyDoc, bizDoc, skyownerRQ);
        });

        if (action == "update") {
            company.setStakeholderCompanyDocsSet(companyDocs);
        } else {
            company.setStakeholderCompanyDocPut(companyDocs);
        }

    }
    private void storeCompanyDocsLocale(StakeholderCompanyDocsEntity doc, BussinessDocEntity bizDoc, SkyownerRegisterRQ skyownerRQ) {

        List<BussinessDocLocaleEntity> bizDocLocale = bussinessDocLocaleRP.findByBusinessDocId(bizDoc.getId());

        List<StakeholderCompanyDocsLocaleEntity> docLocales = new ArrayList<>();
        bizDocLocale.forEach(item -> {

            BussinessTypeLocaleEntity bizLocale = bussinessTypeLocaleRP.findByBusinessTypeIdAndLocaleId(skyownerRQ.getBusinessTypeId(), item.getLocaleId());

            var docLocale = new StakeholderCompanyDocsLocaleEntity();
            docLocale.setLocaleId(item.getLocaleId());
            docLocale.setCompanyDocs(doc);
            docLocale.setDocName(item.getName());
            docLocale.setType(bizLocale != null ? bizLocale.getName() : "");

            docLocales.add(docLocale);


        });

        doc.setCompanyDocsLocale(docLocales);
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
            throw new UnauthorizedException("usr_no_pms", null);
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
    public void addStaff(StakeholderUserInvitationEntity userInv, Long skyuserId) {

        StakeholderUserHasCompanyEntity companyHasUser = new StakeholderUserHasCompanyEntity();
        companyHasUser.setStakeholderCompanyId(userInv.getStakeholderCompanyId());
        companyHasUser.setStakeholderUserId(skyuserId);
        companyHasUser.setStatus(2);
        companyHasUser.setSkyuserRole(userInv.getSkyuserRole());
        companyHasUser.setAddedBy(userInv.getInviteFrom());

        companyHasUserRP.save(companyHasUser);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Validation for documnets skyowner
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param id (id of bussiness type)
     * @Param licenses
     */
    public void licenseValid(Long id, Map<Long, MultipartFile> licenses) {

        licenses.forEach((k, v) -> {
            generalBean.errorMultipart(v);
            BussinessDocEntity existLc = bussinessDocRP.findFirstById(k);
            if (existLc == null) {
                throw new BadRequestException("lc_inc", null);
            }
        });

        Optional<BussinessTypeEntity> bizType = bussinessTypeRP.findById(id);

        if (bizType.isEmpty()) {
            throw new BadRequestException("sth_w_w", null);
        }
        if(bizType.get().getBussinessDocs().size() == 0 && licenses != null) {
            throw new BadRequestException("lc_no_need", null);
        }

        for(BussinessDocEntity doc : bizType.get().getBussinessDocs()) {
            if (doc.getIsRequired() == 1) {
                if(licenses.get(doc.getId()) == null) {
                    throw new BadRequestException("lc_need" + doc.getId(), null);
                }
            }
        }


    }


}
