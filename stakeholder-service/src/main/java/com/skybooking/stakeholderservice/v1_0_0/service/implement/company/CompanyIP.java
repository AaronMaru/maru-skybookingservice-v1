package com.skybooking.stakeholderservice.v1_0_0.service.implement.company;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.BussinessDocEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.BussinessTypeEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.contact.ContactEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.BussinessDocRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.BussinessTypeRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.contact.ContactRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.company.CompanySV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyUpdateRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SkyownerRegisterRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.BussinessDocRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.BussinessTypeRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.CompanyRS;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyowner.SkyownerBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class CompanyIP implements CompanySV {

    @Autowired
    private UserBean userBean;

    @Autowired
    private SkyownerBean skyownerBean;

    @Autowired
    private ContactRP contactRP;

    @Autowired
    private CompanyRP companyRP;

    @Autowired
    private Environment environment;

    @Autowired
    private BussinessTypeRP bussinessTypeRP;

    @Autowired
    private BussinessDocRP bussinessDocRP;

    @Autowired
    private ApiBean apiBean;

    @Autowired
    private GeneralBean generalBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update company profile
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param companyRQ
     * @Param id
     * @Return ResponseEntity
     */
    public CompanyRS updateCompany(CompanyUpdateRQ companyRQ, Long id) {

        UserEntity user = userBean.getUserPrincipal();
        StakeholderCompanyEntity company = skyownerBean.findCompany(user, id);

        if (company.getStatus() == Integer.parseInt(environment.getProperty("spring.companyStatus.waiting"))) {
            throw new BadRequestException("sth_w_w", "");
        }

        statusCompanyVld(company, companyRQ);

        setByFields(companyRQ, company);

        List<ContactEntity> contacts = updateContacts(company, companyRQ);

        if (company.getStatus() == Integer.parseInt(environment.getProperty("spring.companyStatus.reject"))) {

            Optional<BussinessTypeEntity> bizType = bussinessTypeRP.findById(companyRQ.getBusinessTypeId());
            if (bizType.get().getName().equals("Goverment")) {
                company.setContactPerson(companyRQ.getContactPerson());
                company.setContactPosition(companyRQ.getContactPosition());
            }

            company.getStakeholderCompanyDocs().removeAll(company.getStakeholderCompanyDocs());
            company.setBussinessTypeId(companyRQ.getBusinessTypeId());
            updateLicense(company, companyRQ, user.getId());

        }

        if (companyRQ.getProfile() != null) {
            String fileName = userBean.uploadFileForm(companyRQ.getProfile(), "/company_profiles/origin", "/company_profiles/_thumbnail");
            company.setProfileImg(fileName);
        }

        contactRP.saveAll(contacts);
        companyRP.save(company);

        if (companyRQ.getCountryId() != null) {
            apiBean.storeOrUpdateCountry(companyRQ.getCountryId(), "company", company.getId());
        }

        CompanyRS companyRS = userBean.companiesDetails(user.getStakeHolderUser().getStakeholderCompanies()).stream().findFirst().get();

        return companyRS;

    }


    public void setByFields(CompanyUpdateRQ companyRQ, StakeholderCompanyEntity company) {

        if (companyRQ.getBusinessName() != null && !companyRQ.getBusinessName().equals("")) {
            company.setCompanyName(companyRQ.getBusinessName());
        }
        if (companyRQ.getDescription() != null && !companyRQ.getDescription().equals("")) {
            company.setDescription(companyRQ.getDescription());
        }

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update company contacts
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param company
     * @Param companyRQ
     * @Return ContactEntity
     */
    public List<ContactEntity> updateContacts(StakeholderCompanyEntity company, CompanyUpdateRQ companyRQ) {

        List<ContactEntity> contacts = contactRP.getContactCM(company.getId());
        for (ContactEntity contact: contacts) {
            switch (contact.getType()) {
                case "p" :
                    if (companyRQ.getPhone() != null && !companyRQ.getPhone().equals("")) {
                        contact.setValue(companyRQ.getCode() + "-" + companyRQ.getPhone());
                    }
                    break;
                case "a" :
                    if (companyRQ.getAddress() != null && !companyRQ.getAddress().equals("")) {
                        contact.setValue(companyRQ.getAddress());
                    }
                    break;
                case "e" :
                    if (companyRQ.getEmail() != null && !companyRQ.getEmail().equals("")) {
                        contact.setValue(companyRQ.getEmail());
                    }
                    break;
                case "w" :
                    if (companyRQ.getWebsite() != null && !companyRQ.getWebsite().equals("")) {
                        contact.setValue(companyRQ.getWebsite());
                    }
                    break;
                case "z" :
                    if (companyRQ.getPostalOrZipCode() != null && !companyRQ.getPostalOrZipCode().equals("")) {
                        contact.setValue(companyRQ.getPostalOrZipCode());
                    }
                    break;
                case "c" :
                    if (companyRQ.getCity() != null && !companyRQ.getCity().equals("")) {
                        contact.setValue(companyRQ.getCity());
                    }
                    break;
            }

        }

        return contacts;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update company contacts
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param company
     * @Param companyRQ
     * @Return ContactEntity
     */
    public void updateLicense(StakeholderCompanyEntity company, CompanyUpdateRQ companyRQ, Long useID) {

        companyRQ.getLicenses().forEach((k, file) -> {
            BussinessDocEntity existLc = bussinessDocRP.findFirstByName(k);
            if (existLc == null) {
                throw new BadRequestException("Your license is not correct ", "");
            }
            generalBean.errorMultipart(file);
        });

        SkyownerRegisterRQ skyownerRQ = new SkyownerRegisterRQ();
        BeanUtils.copyProperties(companyRQ, skyownerRQ);
        skyownerBean.storeCompanyDocs(company, skyownerRQ, "update");

        company.setStatus(Integer.parseInt(environment.getProperty("spring.companyStatus.waiting")));
        skyownerBean.storeCompanyStatus(company.getId(), useID, 0, "Waiting admin approve");

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get bussiness type
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param company
     * @Param companyRQ
     * @Return ContactEntity
     */
    public List<BussinessTypeRS> bussinessTypes() {
        List<BussinessTypeEntity> businessTypes = bussinessTypeRP.findAllByStatus(1);
        List<BussinessTypeRS> bussinessTypesRS = new ArrayList<>();

        for (BussinessTypeEntity businessType : businessTypes) {
            BussinessTypeRS bussinessTypeRS = new BussinessTypeRS();
            BeanUtils.copyProperties(businessType, bussinessTypeRS);

            bussinessTypesRS.add(bussinessTypeRS);
        }

        return bussinessTypesRS;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get bussiness docs
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param id
     * @Return List<BussinessDocRS>
     */
    public List<BussinessDocRS> bussinessDoc(Long id) {
        Optional<BussinessTypeEntity> bussinessType = bussinessTypeRP.findById(id);

        if (bussinessType.isEmpty()) {
            throw new BadRequestException("sth_w_w", "");
        }

        List<BussinessDocRS> BussinessDocsRS = new ArrayList<>();
        for (BussinessDocEntity doc : bussinessType.get().getBussinessDocs()) {
            BussinessDocRS bussinessDocRS = new BussinessDocRS();
            BeanUtils.copyProperties(doc, bussinessDocRS);

            BussinessDocsRS.add(bussinessDocRS);
        }

        return BussinessDocsRS;
    }


    public void statusCompanyVld(StakeholderCompanyEntity company, CompanyUpdateRQ companyRQ) {
        if (company.getStatus() == 1) {
            if (companyRQ.getBusinessTypeId() == null || companyRQ.getBusinessTypeId().equals("")) {
                throw new BadRequestException("Please provide business type id", "");
            }
        }
    }


}
