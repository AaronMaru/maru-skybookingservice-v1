package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AccountingLines implements Serializable {
    @JsonProperty("AccountingLine")
    private List<AccountingLine> accountingLine;
}
