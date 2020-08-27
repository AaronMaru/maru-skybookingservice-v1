package com.skybooking.skypointservice.v1_0_0.service.topUp;

import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.OfflineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.OnlineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp.PostOnlineTopUpRQ;
import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface TopUpSV {
    StructureRS offlineTopUp(OfflineTopUpRQ offlineTopUpRQ);

    StructureRS preTopUp(OnlineTopUpRQ onlineTopUpRQ);

    StructureRS proceedTopUp(OnlineTopUpRQ OnlineTopUpRQ);

    StructureRS postTopUp(PostOnlineTopUpRQ postOnlineTopUpRQ);
}
