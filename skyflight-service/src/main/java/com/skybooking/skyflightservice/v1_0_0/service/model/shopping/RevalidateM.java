package com.skybooking.skyflightservice.v1_0_0.service.model.shopping;

import lombok.Data;

@Data
public class RevalidateM {

    private int status;
    private String message;

    public RevalidateM(int status) {
        this.status = status;
    }

    public RevalidateM(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
