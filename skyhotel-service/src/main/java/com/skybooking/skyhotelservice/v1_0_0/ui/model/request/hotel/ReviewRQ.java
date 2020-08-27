package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel;

import com.skybooking.core.validators.annotations.IsIn;
import com.skybooking.skyhotelservice.exception.anotation.IsDecimal;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ReviewRQ {

    @IsIn(contains = {"TRIPADVISOR", "HOTELBEDS"}, caseSensitive = true)
    private String type;

    @IsDecimal
    @PositiveOrZero
    @Max(5)
    private Double maxRate;

    @Min(1)
    private Long minReviewCount;

}
