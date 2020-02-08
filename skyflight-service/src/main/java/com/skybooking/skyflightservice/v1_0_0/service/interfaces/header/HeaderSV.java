package com.skybooking.skyflightservice.v1_0_0.service.interfaces.header;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public interface HeaderSV {

    Boolean check(HttpHeaders headers);

}
