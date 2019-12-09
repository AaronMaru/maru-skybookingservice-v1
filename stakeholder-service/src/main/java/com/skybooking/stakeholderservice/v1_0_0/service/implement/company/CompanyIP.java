package com.skybooking.stakeholderservice.v1_0_0.service.implement.company;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyDocsEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.StakeholderCompanyEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.contact.ContactEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.contact.ContactRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.company.CompanySV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.CompanyRS;
import com.skybooking.stakeholderservice.v1_0_0.util.skyowner.SkyownerBean;
import com.skybooking.stakeholderservice.v1_0_0.util.skyuser.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.util.List;


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
        company.setDescription(companyRQ.getDescription());

        if (companyRQ.getBusinessType().equals("com_gov")) {
            company.setContactPerson(companyRQ.getContactPerson());
            company.setContactPosition(companyRQ.getContactPosition());
        }

        List<ContactEntity> contacts = updateContacts(company, companyRQ);
        if (company.getStatus() == Integer.parseInt(environment.getProperty("spring.companyStatus.reject"))) {
            company.setTypeValue(companyRQ.getBusinessType());
            updateLicense(company, companyRQ, user.getId());
        }

        contactRP.saveAll(contacts);
        companyRP.save(company);

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
            if (contact.getType().equals("e")) {
                contact.setValue(companyRQ.getEmail());
            }
            if (contact.getType().equals("a")) {
                contact.setValue(companyRQ.getAddress());
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

        String licenseName = company.getStakeholderCompanyDocs().stream().findFirst().get().getImage();

        userBean.uploadLicense(companyRQ.getLicense(), "/company_license", licenseName);

        if (companyRQ.getBusinessType().equals("com_tra")) {
            if (company.getStakeholderCompanyDocs().size() < 2) {
                StakeholderCompanyDocsEntity docsTwo = new StakeholderCompanyDocsEntity();
                String nameFile2 = userBean.uploadLicense(companyRQ.getLicenseSecond(), "/company_license", null);
                docsTwo.setImage(nameFile2);

                docsTwo.setStakeholderCompany(company);
                company.getStakeholderCompanyDocs().add(docsTwo);
            } else {
                String licenseName2 = company.getStakeholderCompanyDocs().stream().skip(company.getStakeholderCompanyDocs().size() -1).findFirst().get().getImage();
                userBean.uploadLicense(companyRQ.getLicense(), "/company_license", licenseName2);
            }
        }

        company.setStatus(Integer.parseInt(environment.getProperty("spring.companyStatus.waiting")));
        skyownerBean.storeCompanyStatus(company.getId(), useID, 0, "Waiting admin approve");

    }




}
