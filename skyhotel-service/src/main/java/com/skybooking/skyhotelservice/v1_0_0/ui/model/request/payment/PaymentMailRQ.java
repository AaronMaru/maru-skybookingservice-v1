package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.payment;

import com.skybooking.skyhotelservice.exception.Include;
import lombok.Data;

@Data
public class PaymentMailRQ {

    private String email;
    private String bookingCode;
    private String lang;

    @Include(contains = "voucher|e-receipt", delimiter = "\\|", message = "Type is invalid")
    private String type = "all";

    public PaymentMailRQ() {}

    public PaymentMailRQ(String email, String bookingCode, String lang) {
        this.email = email;
        this.bookingCode = bookingCode;
        this.lang = lang;
    }

}
