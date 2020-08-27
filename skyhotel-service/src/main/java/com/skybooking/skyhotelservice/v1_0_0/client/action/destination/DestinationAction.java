package com.skybooking.skyhotelservice.v1_0_0.client.action.destination;

import com.skybooking.skyhotelservice.constant.EndpointConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.action.BaseAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.StructureRSDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.destination.DestinationRQDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.destination.DestinationRSDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class DestinationAction extends BaseAction {

    @Autowired
    private HttpServletRequest servletRequest;

    public DestinationRSDS quickSearch(DestinationRQDS request) {
        try {

            return client
                .mutate()
                .build()
                .post()
                .uri(appConfig.getDISTRIBUTED_URL() + EndpointConstant.QuickSearchDestination.V1_0_0)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                .header("X-Localization", servletRequest.getHeader("X-Localization"))
                .bodyValue(request)
                .retrieve()
                .bodyToMono(DestinationRSDS.class)
                .block();

        } catch (Exception exception) {

            return null;

        }
    }

    public DestinationRSDS autocompleteSearch(String keyword) {
        try {

            return client
                .mutate()
                .build()
                .get()
                .uri(appConfig.getDISTRIBUTED_URL() + EndpointConstant.AutocompleteSearchDestination.V1_0_0 + "?keyword=" + keyword)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                .header("X-Localization", servletRequest.getHeader("X-Localization"))
                .retrieve()
                .bodyToMono(DestinationRSDS.class)
                .block();

        } catch (Exception exception) {

            return null;

        }
    }

}
