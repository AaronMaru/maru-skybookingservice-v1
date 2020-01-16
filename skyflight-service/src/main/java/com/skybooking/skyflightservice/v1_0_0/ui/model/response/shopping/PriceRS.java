package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

@Data
public class PriceRS {

    private String id;
    private String type;
    private String tax;
    private String baseFare;
    private String quantity;

}
