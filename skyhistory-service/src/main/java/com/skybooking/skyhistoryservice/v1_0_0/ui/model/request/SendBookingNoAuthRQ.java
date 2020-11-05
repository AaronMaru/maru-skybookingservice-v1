package com.skybooking.skyhistoryservice.v1_0_0.ui.model.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class SendBookingNoAuthRQ {

    private String bookingCode;
    private String email;
    private Integer skyuserId;
    private Integer companyId;
    private boolean isAdmin;

}
