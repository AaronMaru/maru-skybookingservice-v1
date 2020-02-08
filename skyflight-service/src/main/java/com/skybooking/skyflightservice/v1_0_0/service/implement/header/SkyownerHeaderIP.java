package com.skybooking.skyflightservice.v1_0_0.service.implement.header;

import com.skybooking.skyflightservice.v1_0_0.service.interfaces.header.SkyownerHeaderSV;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class SkyownerHeaderIP implements SkyownerHeaderSV {


    @Override
    public Boolean check(HttpHeaders headers) {

        if (!headers.containsKey("x-companyid")) {
            return false;
        }

        return true;
    }
}
