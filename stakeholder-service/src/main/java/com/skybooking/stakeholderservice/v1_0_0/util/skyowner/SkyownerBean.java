package com.skybooking.stakeholderservice.v1_0_0.util.skyowner;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.*;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.StakeHolderUserEntity;
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

        Optional<BussinessTypeEntity> bizType = bussinessTypeRP.findById(skyownerRQ.getBusinessTypeId());
        if (bizType.get().getName().equals("Goverment")) {
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

        storeCompanyDocs(stkCompany, skyownerRQ, "add");

        user = userRP.save(user);

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
        apiBean.addSimpleContact(id,skyownerRQ.getCode() + "-" + skyownerRQ.getPhone().replaceFirst("^0+(?!$)", ""), "p", "skyowner");
        apiBean.addSimpleContact(id, skyownerRQ.getWebsite(), "w", "skyowner");
        apiBean.addSimpleContact(id, skyownerRQ.getPostalOrZipCode(), "z", "skyowner");
        apiBean.addSimpleContact(id, skyownerRQ.getEmail(), "e", "skyowner");
        apiBean.addSimpleContact(id, skyownerRQ.getAddress(), "a", "skyowner");
        apiBean.addSimpleContact(id, skyownerRQ.getCity(), "c", "skyowner");
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

            BussinessDocEntity bizDoc = bussinessDocRP.findFirstByName(key);
            String nameFile = userBean.uploadLicense(file, "/company_license", null);

            companyDoc.setFileName(nameFile);
            companyDoc.setDocName(key);
            companyDoc.setIsRequired(bizDoc.getIsRequired());

            companyDocs.add(companyDoc);

        });

        for (StakeholderCompanyDocsEntity doc: companyDocs) {
            doc.setStakeholderCompany(company);
        }

        if (action == "update") {
            company.setStakeholderCompanyDocsSet(companyDocs);
        } else {
            company.setStakeholderCompanyDocPut(companyDocs);
        }

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
    public void addStaff(Long companyId, Long skyuserId, String role) {

        StakeholderUserHasCompanyEntity companyHasUser = new StakeholderUserHasCompanyEntity();
        companyHasUser.setStakeholderCompanyId(companyId);
        companyHasUser.setStakeholderUserId(skyuserId);
        companyHasUser.setStatus(2);
        companyHasUser.setSkyuserRole(role);

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
    public void licenseValid(Long id, Map<String, MultipartFile> licenses) {

        licenses.forEach((k, v) -> {
            generalBean.errorMultipart(v);
            BussinessDocEntity existLc = bussinessDocRP.findFirstByName(k);
            if (existLc == null) {
                throw new BadRequestException("Your license is not correct ", "");
            }
        });

        Optional<BussinessTypeEntity> bizType = bussinessTypeRP.findById(id);

        if (bizType.isEmpty()) {
            throw new BadRequestException("sth_w_w", "");
        }
        if(bizType.get().getBussinessDocs().size() == 0 && licenses != null) {
            throw new BadRequestException("Please dont provide the license", "");
        }

        for(BussinessDocEntity doc : bizType.get().getBussinessDocs()) {
            if (doc.getIsRequired() == 1) {
                if(licenses.get(doc.getName()) == null) {
                    throw new BadRequestException("Please provide a " + doc.getName(), "");
                }
            }
        }

    }


}
