package com.skybooking.eventservice.v1_0_0.ui.model.request.hotel;

import com.skybooking.eventservice.exception.Include;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PaymentInfoRQ extends PaymentBaseForm {

    @Email
    @NotNull(message = "The email code can not be null")
    @NotEmpty(message = "The email code can not be empty")
    private String email;

    @Include(contains = "voucher|e-receipt|all", delimiter = "\\|", message = "Grant type is invalid")
    private String type = "all";

    //for e-receipt
    private String customerName = "";
    private String billingAddress = "";
    private String emailAddress = "";
    private String chargeDate = "";

    //for voucher
    private String bookingReference = "";
    private String clientFullname = "";
    private String clientId = "";
    private String countryResident = "";
    private String address = "";
    private String property = "Skybooking Hotel";
    private String propertyContactNumber = "";
    private String arrival = "";
    private String departure = "";

    private String paymentMethod = "";
    private String cardNo = "";
    private String expired = "";

    private String remark = "";
    private Integer numAdult = 0;
    private Integer numChildren = 0;
    private Integer numPromotion = 0;
    private String url;

}
