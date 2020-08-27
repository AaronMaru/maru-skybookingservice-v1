package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.notification;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.passenger.PassengerRS;
import lombok.Data;

import java.util.List;

@Data
public class NotificationPagingRS {

    private Integer size;
    private Integer page;
    private Long totals;
    private Integer unRead;

    private List<NotificationRS> data;

}
