package com.skybooking.stakeholderservice.v1_0_0.ui.controller.backoffice.authentication;

import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.backoffice.authentication.TokenSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.backoffice.authentication.TokenRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.structure.StructureRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("v1.0.0/back-office/oauth/token")
public class TokenController {

    @Autowired private TokenSV tokenSV;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * fetch token
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param tokenRQ TokenRQ
     * @return ResponseEntity
     */
    @PostMapping()
    public ResponseEntity<StructureRS> fetchToken(@Valid @RequestBody TokenRQ tokenRQ) {

        StructureRS response = tokenSV.fetchToken(tokenRQ);

        return ResponseEntity
            .status(response.getStatus())
            .body(response);
    }
}
