package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.setting;

import java.util.HashMap;

public class SettingRS {

    HashMap<String, String> facebookDetails;
    HashMap<String, String> twitterDetails;
    HashMap<String, String> seoDetails;
    HashMap<String, String> thirdPartyDetails;
    HashMap<String, String> appstoreDetails;
    HashMap<String, String> socialMediaDetails;
    HashMap<String, String> contactDetails;

    public HashMap<String, String> getFacebookDetails() {
        return facebookDetails;
    }

    public void setFacebookDetails(HashMap<String, String> facebookDetails) {
        this.facebookDetails = facebookDetails;
    }

    public HashMap<String, String> getTwitterDetails() {
        return twitterDetails;
    }

    public void setTwitterDetails(HashMap<String, String> twitterDetails) {
        this.twitterDetails = twitterDetails;
    }

    public HashMap<String, String> getSeoDetails() {
        return seoDetails;
    }

    public void setSeoDetails(HashMap<String, String> seoDetails) {
        this.seoDetails = seoDetails;
    }

    public HashMap<String, String> getThirdPartyDetails() {
        return thirdPartyDetails;
    }

    public void setThirdPartyDetails(HashMap<String, String> thirdPartyDetails) {
        this.thirdPartyDetails = thirdPartyDetails;
    }

    public HashMap<String, String> getAppstoreDetails() {
        return appstoreDetails;
    }

    public void setAppstoreDetails(HashMap<String, String> appstoreDetails) {
        this.appstoreDetails = appstoreDetails;
    }

    public HashMap<String, String> getSocialMediaDetails() {
        return socialMediaDetails;
    }

    public void setSocialMediaDetails(HashMap<String, String> socialMediaDetails) {
        this.socialMediaDetails = socialMediaDetails;
    }

    public HashMap<String, String> getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(HashMap<String, String> contactDetails) {
        this.contactDetails = contactDetails;
    }
}
