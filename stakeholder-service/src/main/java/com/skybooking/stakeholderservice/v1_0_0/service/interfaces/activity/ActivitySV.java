package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.activity;

public interface ActivitySV {
    void logName(String name);

    void causedBy(Long id, String type);

    void performedOn(Long id, String type);

    void log(String message);
}
