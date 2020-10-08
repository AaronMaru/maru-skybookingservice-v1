package com.skybooking.skypointservice.v1_0_0.service.topUp;

import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.ConfirmOfflineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.OfflineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.OnlineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.PostOnlineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface TopUpSV {
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Offline top up
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param offlineTopUpRQ     OfflineTopUpRQ
     * @return StructureRS
     */
    StructureRS offlineTopUp(HttpServletRequest httpServletRequest, OfflineTopUpRQ offlineTopUpRQ);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Backend: Offline top up confirm
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest    HttpServletRequest
     * @param confirmOfflineTopUpRQ ConfirmOfflineTopUpRQ
     * @return StructureRS
     */
    StructureRS confirmOfflineTopUp(HttpServletRequest httpServletRequest, ConfirmOfflineTopUpRQ confirmOfflineTopUpRQ);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Fronted: Calculate point before proceed top up
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param onlineTopUpRQ      OnlineTopUpRQ
     * @return StructureRS
     */
    StructureRS preTopUp(HttpServletRequest httpServletRequest, OnlineTopUpRQ onlineTopUpRQ);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Fronted: Save transactions online top up and return payment url
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param onlineTopUpRQ      OnlineTopUpRQ
     * @return StructureRS
     */
    StructureRS proceedTopUp(HttpServletRequest httpServletRequest, OnlineTopUpRQ onlineTopUpRQ);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Common: Call by payment-service to update online top up transaction
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param httpServletRequest HttpServletRequest
     * @param postOnlineTopUpRQ  PostOnlineTopUpRQ
     * @return StructureRS
     */
    StructureRS postTopUp(HttpServletRequest httpServletRequest, PostOnlineTopUpRQ postOnlineTopUpRQ);
}
