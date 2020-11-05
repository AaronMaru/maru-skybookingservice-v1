package com.skybooking.backofficeservice.v1_0_0.client.model.response.account;

import com.skybooking.backofficeservice.v1_0_0.client.model.response.StructureServiceRS;
import lombok.Data;

@Data
public class AccountStructureServiceRS extends StructureServiceRS {
    private AccountDetailServiceRS data;
}
