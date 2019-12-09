package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class Brand {

    @JsonProperty("programId")
    private String programId;

    @JsonProperty("programCode")
    private String programCode;

    @JsonProperty("programDescription")
    private String programDescription;

    @JsonProperty("programSystemCode")
    private String programSystemCode;

    @JsonProperty("brandName")
    private String brandName;

    @JsonProperty("code")
    private String code;
}
