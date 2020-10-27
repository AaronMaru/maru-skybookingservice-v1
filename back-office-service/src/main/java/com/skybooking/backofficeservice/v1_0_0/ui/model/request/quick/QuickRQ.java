package com.skybooking.backofficeservice.v1_0_0.ui.model.request.quick;

import com.skybooking.backofficeservice.exception.anotation.quick.IsUnique;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class QuickRQ {

    @NotBlank(message = "destination code is required.")
    @IsUnique(destinationCode = "#destinationCode")
    private String destinationCode;
    @NotBlank(message = "destination type is required.")
    private String destinationType;
}
