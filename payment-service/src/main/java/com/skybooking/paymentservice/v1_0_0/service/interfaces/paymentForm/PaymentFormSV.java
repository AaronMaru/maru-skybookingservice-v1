package com.skybooking.paymentservice.v1_0_0.service.interfaces.paymentForm;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface PaymentFormSV {
    String pipayFormHotel(Map<String, String> request, Model model);
}
