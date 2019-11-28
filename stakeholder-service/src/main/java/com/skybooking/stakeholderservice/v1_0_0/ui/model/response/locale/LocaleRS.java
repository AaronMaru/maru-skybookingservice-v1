package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.locale;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LocaleRS {

    private Long id;
    private String name;
    @JsonProperty(value = "localeName")
    private String nativeName;
    private String locale;

}
