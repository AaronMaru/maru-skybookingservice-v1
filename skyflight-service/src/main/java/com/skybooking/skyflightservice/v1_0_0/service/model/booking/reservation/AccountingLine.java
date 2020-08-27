package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AccountingLine implements Serializable {

    private String elementId;
    @JsonProperty("DocumentNumber")
    private String documentNumber;
    @JsonProperty("TaxAmount")
    private BigDecimal taxAmount = BigDecimal.ZERO;
    @JsonProperty("FareApplication")
    private String fareApplication;
    private int index;
    @JsonProperty("PassengerName")
    private String passengerName;
    @JsonProperty("FormOfPaymentCode")
    private String formOfPaymentCode;
    @JsonProperty("BaseFare")
    private BigDecimal baseFare = BigDecimal.ZERO;
    @JsonProperty("AirlineDesignator")
    private String airlineDesignator;
    @JsonProperty("CommissionAmount")
    private BigDecimal commissionAmount = BigDecimal.ZERO;
    @JsonProperty("TarriffBasis")
    private String tarriffBasis;
    private int id;
    @JsonProperty("NumberOfConjunctedDocuments")
    private int numberOfConjunctedDocuments;

}
