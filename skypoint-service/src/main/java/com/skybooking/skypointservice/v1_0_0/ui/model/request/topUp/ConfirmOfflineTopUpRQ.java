package com.skybooking.skypointservice.v1_0_0.ui.model.request.topUp;

import com.skybooking.core.validators.annotations.IsIn;
import com.skybooking.core.validators.annotations.IsNotEmpty;
import lombok.Data;

@Data
public class ConfirmOfflineTopUpRQ {
    private String transactionCode;

    @IsNotEmpty
    @IsIn(contains = {"APPROVED", "REJECTED"}, message = "status_not_valid")
    private String status;
}
