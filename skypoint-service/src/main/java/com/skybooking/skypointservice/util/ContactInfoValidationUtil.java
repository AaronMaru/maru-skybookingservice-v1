package com.skybooking.skypointservice.util;


import com.skybooking.skypointservice.v1_0_0.ui.model.request.ContactInfo;

import java.util.Arrays;
import java.util.List;

public class ContactInfoValidationUtil {
    public static void contactInfoValidation(ContactInfo contactInfo) {
        List<String> contactInfoKey = Arrays.asList("email", "name", "phoneCode", "phoneNumber");
        ValidateKeyUtil.validateKey(contactInfo, contactInfoKey);
        ValidateKeyUtil.validatePhoneNumber(contactInfo.getPhoneNumber());
        ValidateKeyUtil.validateEmail(contactInfo.getEmail());
        ValidateKeyUtil.validatePhoneCode(contactInfo.getPhoneCode());
    }
}
