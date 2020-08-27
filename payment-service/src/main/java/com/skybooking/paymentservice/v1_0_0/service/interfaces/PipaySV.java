package com.skybooking.paymentservice.v1_0_0.service.interfaces;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface PipaySV {

    void paymentFail(Map<String, Object> request);

}
