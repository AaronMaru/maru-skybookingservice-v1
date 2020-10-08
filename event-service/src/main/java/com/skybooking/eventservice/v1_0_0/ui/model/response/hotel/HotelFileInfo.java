package com.skybooking.eventservice.v1_0_0.ui.model.response.hotel;

import lombok.Data;

import java.io.File;

@Data
public class HotelFileInfo {
    private Receipt type;
    private Voucher voucher;

    @Data
    class Voucher {
        private String fileType;
        private File file;
    }

    @Data
    class Receipt {
        private String fileType;
        private File file;
    }
}
