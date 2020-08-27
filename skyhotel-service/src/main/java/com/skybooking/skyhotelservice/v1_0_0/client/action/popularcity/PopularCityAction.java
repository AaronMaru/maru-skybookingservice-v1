package com.skybooking.skyhotelservice.v1_0_0.client.action.popularcity;

import com.skybooking.skyhotelservice.constant.EndpointConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.action.BaseAction;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.popularcity.PopularCityRQDS;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.popularCity.PopularCityRSDS;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class PopularCityAction extends BaseAction {

    private final HttpServletRequest servletRequest;

    public PopularCityRSDS getPopularCity(PopularCityRQDS request) {
        return client
            .mutate()
            .build()
            .post()
            .uri(appConfig.getDISTRIBUTED_URL() + EndpointConstant.PopularCity.V1_0_0)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
            .header("X-Localization", servletRequest.getHeader("X-Localization"))
            .bodyValue(request)
            .retrieve()
            .bodyToMono(PopularCityRSDS.class)
            .block();
    }

}
