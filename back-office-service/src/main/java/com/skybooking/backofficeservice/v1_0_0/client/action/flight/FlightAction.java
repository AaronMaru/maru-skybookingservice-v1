package com.skybooking.backofficeservice.v1_0_0.client.action.flight;

import com.skybooking.backofficeservice.constant.EndpointConstant;
import com.skybooking.backofficeservice.constant.MessageConstant;
import com.skybooking.backofficeservice.exception.httpstatus.InternalServerError;
import com.skybooking.backofficeservice.exception.httpstatus.NotFoundException;
import com.skybooking.backofficeservice.v1_0_0.client.action.BaseAction;
import com.skybooking.backofficeservice.v1_0_0.client.model.response.flight.FlightStructureServiceRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.servlet.http.HttpServletRequest;

@Component
public class FlightAction extends BaseAction {

    @Autowired
    private HttpServletRequest httpServletRequest;

    public FlightStructureServiceRS getFlightDetail(String flightBookingCode)
    {
        try {
            return client
                .mutate()
                .build()
                .get()
                .uri(appConfig.getSKY_URL() + EndpointConstant.FlightBookingHistoryDetail.V1_0_0 + flightBookingCode)
                .header(HttpHeaders.AUTHORIZATION, httpServletRequest.getHeader("Authorization"))
                .retrieve()
                .bodyToMono(FlightStructureServiceRS.class)
                .block();

        }catch (WebClientResponseException exception){
            if (exception instanceof WebClientResponseException.NotFound)
                throw new NotFoundException(MessageConstant.DATA_NOT_FOUND, null);
            if (exception instanceof WebClientResponseException.InternalServerError)
                    throw new InternalServerError(MessageConstant.DATA_NOT_FOUND,null);
            throw exception;
        }
    }
}
