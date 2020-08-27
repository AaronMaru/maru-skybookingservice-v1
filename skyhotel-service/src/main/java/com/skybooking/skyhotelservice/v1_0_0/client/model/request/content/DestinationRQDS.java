package com.skybooking.skyhotelservice.v1_0_0.client.model.request.content;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationRQDS {

    private String code;
    private String group;
    private String hotelCode;

    public DestinationRQDS(String code) {
        this.code = code;
    }
}
