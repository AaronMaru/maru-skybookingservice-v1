package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.cache;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CacheSource implements Serializable {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("partition")
    private String partition;

    @JsonProperty("priority")
    private Integer priority;

    @JsonProperty("version")
    private String version;
}
