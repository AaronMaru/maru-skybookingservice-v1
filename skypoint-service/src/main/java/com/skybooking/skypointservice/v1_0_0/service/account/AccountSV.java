package com.skybooking.skypointservice.v1_0_0.service.account;

import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface AccountSV {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Frontend: Get account balance
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return StructureRS
     */
    StructureRS getBalance(HttpServletRequest httpServletRequest);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Get account balance info
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return StructureRS
     */
    StructureRS getSkyOwnerAccountBalanceInfo(HttpServletRequest httpServletRequest, String userCode);
}
