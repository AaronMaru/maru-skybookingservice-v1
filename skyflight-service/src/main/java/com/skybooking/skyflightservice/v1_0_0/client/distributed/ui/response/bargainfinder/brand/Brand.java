package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data

public class Brand implements Serializable {

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
