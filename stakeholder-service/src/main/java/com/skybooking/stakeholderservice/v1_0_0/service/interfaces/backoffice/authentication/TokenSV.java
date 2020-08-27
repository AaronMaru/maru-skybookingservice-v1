package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.backoffice.authentication;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.backoffice.authentication.TokenRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.structure.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface TokenSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * fetch token
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param tokenRQ TokenRQ
     * @return StructureRS
     */
    StructureRS fetchToken(TokenRQ tokenRQ);

}
