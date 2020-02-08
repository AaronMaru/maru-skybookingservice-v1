package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.user;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user.SkyUserRegisterRQ;
import org.springframework.stereotype.Service;

@Service
public interface RegisterSV {

    void skyuser(SkyUserRegisterRQ skyuserRQ, String plateform);

}
