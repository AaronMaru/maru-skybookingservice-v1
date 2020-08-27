package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.PhoneRS;
import lombok.Data;

import java.io.Serializable;

@Data
public class PhoneCached implements Serializable {

    private String phoneNumber;
    private String phoneType;

    public PhoneRS getPhone()
    {
        PhoneRS phoneRS = new PhoneRS();
        phoneRS.setPhoneNumber(getPhoneNumber());
        phoneRS.setPhoneType(getPhoneType());

        return phoneRS;
    }

}
