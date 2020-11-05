package com.skybooking.backofficeservice.v1_0_0.client.action.hotelcontent;

import com.skybooking.backofficeservice.constant.EndpointConstant;
import com.skybooking.backofficeservice.v1_0_0.client.action.BaseAction;
import com.skybooking.backofficeservice.v1_0_0.client.model.request.hotelcontent.HotelContentRQDS;
import com.skybooking.backofficeservice.v1_0_0.client.model.response.structure.StructureRSDS;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class HotelContentAction extends BaseAction {

    public StructureRSDS getHotelContents(HotelContentRQDS request)
    {
        try {
            return client
                .mutate()
                .build()
                .post()
                .uri(appConfig.getDISTRIBUTED_URL() + EndpointConstant.HotelContent.V1_0_0)
                .header(HttpHeaders.AUTHORIZATION, TOKEN_TYPE + dsTokenHolder.getAuth().getAccessToken())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(StructureRSDS.class)
                .block();

        }
        catch (Exception exception)
        {
            throw exception;
        }
    }

}
