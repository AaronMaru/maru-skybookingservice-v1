package com.skybooking.skypointservice.v1_0_0.service.account;

import com.skybooking.skypointservice.v1_0_0.ui.model.response.StructureRS;
import org.springframework.stereotype.Service;

@Service
public interface AccountSV {

    StructureRS getBalance();

    StructureRS backendDashboard();

    StructureRS backendAccountInfo(String userCode);
}
