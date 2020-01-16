package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.brand;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandFeature implements Serializable {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("application")
    private String application;

    @JsonProperty("commercialName")
    private String commercialName;

    @JsonProperty("serviceGroup")
    private String serviceGroup;

    @JsonProperty("serviceType")
    private String serviceType;

    @JsonProperty("subCode")
    private String subCode;

    @JsonProperty("vendor")
    private String vendor;
}
