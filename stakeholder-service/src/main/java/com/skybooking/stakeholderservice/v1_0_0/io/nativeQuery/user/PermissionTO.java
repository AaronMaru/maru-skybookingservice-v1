package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.user;


public class PermissionTO {

    private String userType;
    private String key;
    private String module;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

}
