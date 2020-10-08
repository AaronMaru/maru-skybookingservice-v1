package com.skybooking.skyhotelservice.v1_0_0.client.model.request.event;

import com.skybooking.skyhotelservice.exception.Include;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PaymentInfoRQEV {

    @Email
    @NotNull(message = "The email code can not be null")
    @NotEmpty(message = "The email code can not be empty")
    private String email;

    @Include(contains = "voucher|e-receipt|all", delimiter = "\\|", message = "Grant type is invalid")
    private String type = "all";

    private String bookingCode = "";
    private String hotelName = "Teriblade";
    private Integer period = 0;
    private String roomType = "";
    private Integer numRooms = 0;
    private Integer numExtraBed = 0;
    private BigDecimal totalRoomCharges = BigDecimal.ZERO;
    private BigDecimal totalExtraBedCharges = BigDecimal.ZERO;
    private BigDecimal grandTotal = BigDecimal.ZERO;
    private BigDecimal totalPaymentFee = BigDecimal.ZERO;

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
    private String lang;

}
