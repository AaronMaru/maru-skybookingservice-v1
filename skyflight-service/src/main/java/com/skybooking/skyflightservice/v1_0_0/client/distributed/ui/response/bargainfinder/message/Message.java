package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message implements Serializable {

    @JsonProperty("code")
    private String code;

    @JsonProperty("text")
    private String text;

    @JsonProperty("type")
    private String type;

    @JsonProperty("value")
    private String value;

    @JsonProperty("severity")
    private String severity;
}
