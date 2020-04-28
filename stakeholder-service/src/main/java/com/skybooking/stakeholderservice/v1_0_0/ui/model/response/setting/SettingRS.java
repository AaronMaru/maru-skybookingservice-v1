package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.setting;

import lombok.Data;

import java.util.HashMap;

@Data
public class SettingRS {

    HashMap<String, String> facebookDetails;
    HashMap<String, String> twitterDetails;
    HashMap<String, String> seoDetails;
    HashMap<String, String> thirdPartyDetails;
    HashMap<String, String> appstoreDetails;
    HashMap<String, String> socialMediaDetails;
    HashMap<String, String> contactDetails;
    private String awsUrl;


}
