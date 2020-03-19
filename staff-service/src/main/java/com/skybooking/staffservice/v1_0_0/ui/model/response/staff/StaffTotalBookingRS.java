package com.skybooking.staffservice.v1_0_0.ui.model.response.staff;

import lombok.Data;

@Data
public class StaffTotalBookingRS {

    private AmountRS upcoming;
    private AmountRS completed;
    private AmountRS cancellation;
    private AmountRS failed;

}
