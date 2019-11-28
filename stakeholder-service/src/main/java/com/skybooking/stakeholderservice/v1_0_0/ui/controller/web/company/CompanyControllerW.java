package com.skybooking.stakeholderservice.v1_0_0.ui.controller.web.company;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.company.CompanySV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company.CompanyRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.CompanyRS;
import com.skybooking.stakeholderservice.v1_0_0.util.general.GeneralBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/wv1.0.0")
public class CompanyControllerW {

    @Autowired
    private CompanySV companySV;

    @Autowired
    private GeneralBean generalBean;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Update user profile
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param profileRequest
     * @Return ResponseEntity
     */
    @PatchMapping(value = "/company/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object updateCompany(@ModelAttribute("companyRQ") @Valid CompanyRQ companyRQ, Errors errors, @PathVariable Long id) {
        if(errors.hasErrors()) {
            return generalBean.errors(errors);
        }
        CompanyRS companyRS = companySV.updateCompany(companyRQ, id);
        return new ResponseEntity<>(companyRS, HttpStatus.OK);
    }


}
