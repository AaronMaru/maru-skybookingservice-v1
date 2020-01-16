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
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyRQ;
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
    public CompanyRS updateCompany(CompanyRQ companyRQ, Long id) {

        UserEntity user = userBean.getUserPrincipal();

        StakeholderCompanyEntity company = skyownerBean.findCompany(user, id);

        if (company.getStatus() == Integer.parseInt(environment.getProperty("spring.companyStatus.waiting"))) {
            throw new BadRequestException("sth_w_w", "");
        }

        company.setCompanyName(companyRQ.getBusinessName());

        Optional<BussinessTypeEntity> bizType = bussinessTypeRP.findById(companyRQ.getBusinessTypeId());
        if (bizType.get().getName().equals("Goverment")) {
            company.setContactPerson(companyRQ.getContactPerson());
            company.setContactPosition(companyRQ.getContactPosition());
        }

        List<ContactEntity> contacts = updateContacts(company, companyRQ);
        if (company.getStatus() == Integer.parseInt(environment.getProperty("spring.companyStatus.reject"))) {

            company.getStakeholderCompanyDocs().removeAll(company.getStakeholderCompanyDocs());

            company.setBussinessTypeId(companyRQ.getBusinessTypeId());
            updateLicense(company, companyRQ, user.getId());
        }

        contactRP.saveAll(contacts);
        companyRP.save(company);

        apiBean.storeOrUpdateCountry(companyRQ.getCountryId(), "company", company.getId());

        CompanyRS companyRS = userBean.companiesDetails(user.getStakeHolderUser().getStakeholderCompanies()).stream().findFirst().get();

        return companyRS;
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
    public List<ContactEntity> updateContacts(StakeholderCompanyEntity company, CompanyRQ companyRQ) {
        List<ContactEntity> contacts = contactRP.getContactCM(company.getId());
        for (ContactEntity contact: contacts) {

            if (contact.getType().equals("p")) {
                contact.setValue(companyRQ.getCode() + "-" + companyRQ.getPhone());
            }
            if (contact.getType().equals("a")) {
                contact.setValue(companyRQ.getAddress());
            }
            if (contact.getType().equals("w")) {
                contact.setValue(companyRQ.getWebsite());
            }
            if (contact.getType().equals("z")) {
                contact.setValue(companyRQ.getPostalOrZipCode());
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
    public void updateLicense(StakeholderCompanyEntity company, CompanyRQ companyRQ, Long useID) {

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


}
