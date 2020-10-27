package com.skybooking.backofficeservice.v1_0_0.ui.model.request.destination;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class DestinationRQ {

    private  String destination;

    public DestinationRQ(HttpServletRequest request)
    {
        this.destination = request.getParameter("destination");
    }
}
