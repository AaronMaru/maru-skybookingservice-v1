package com.skybooking.skyhotelservice.v1_0_0.client.action.integration;

import com.skybooking.skyhotelservice.config.AppConfig;
import com.skybooking.skyhotelservice.constant.EndpointConstant;
import com.skybooking.skyhotelservice.v1_0_0.client.model.response.BasicAuthRS;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.token.ServiceTokenEntity;
import com.skybooking.skyhotelservice.v1_0_0.io.repository.token.ServiceTokenRP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
public class DSTokenHolder {

    final static String DISTRIBUTED_SUPPLIER = "distribution-service";
    final static String HOTEL_SERVICE = "basehotelds";

    @Autowired private AppConfig appConfig;

    @Autowired private ServiceTokenRP serviceTokenRP;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get basic authentication
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return BasicAuthRS
     */
    public BasicAuthRS getAuth() {

        // get access token from cached
        var basic = this.getAccessToken();
        if (basic != null) return basic;

        // fetch new access token
        basic = this.fetchAccessToken();

        // store access token
        this.storeAccessToken(basic);

        return basic;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * fetch access token
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return BasicAuthRS
     */
    private BasicAuthRS fetchAccessToken() {

        // add custom headers
        var headers = new HttpHeaders();
        headers.setBasicAuth(appConfig.getAUTH_CLIENT_ID(), appConfig.getAUTH_CLIENT_SECRET());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // add posting data
        var data = new LinkedMultiValueMap<String, String>();
        data.add("grant_type", "client_credentials");

        var request = new HttpEntity<MultiValueMap<String, String>>(data, headers);

        // request access token
        var rest = new RestTemplate();

        var response = rest.postForEntity(appConfig.getDISTRIBUTED_URL() + EndpointConstant.Auth.V1_0_0, request, BasicAuthRS.class);

        return response.getBody();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * store access token
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param basicAuthRS
     */
    private void storeAccessToken(BasicAuthRS basicAuthRS) {

        if (basicAuthRS == null) return;

        var entity = new ServiceTokenEntity();

        entity.setAppId(appConfig.getAPP_ID());
        entity.setSupplier(DISTRIBUTED_SUPPLIER);
        entity.setService(HOTEL_SERVICE);
        entity.setTokenType(basicAuthRS.getTokenType());
        entity.setAccessToken(basicAuthRS.getAccessToken());
        entity.setExpiresIn(basicAuthRS.getExpiresIn());

        serviceTokenRP.save(entity);

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get access token
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return BasicAuthRS
     */
    private BasicAuthRS getAccessToken() {

        var entity = serviceTokenRP.findLastAccessToken(appConfig.getAPP_ID(), DISTRIBUTED_SUPPLIER, HOTEL_SERVICE);
        if (entity == null) return null;

        //TODO: something went wrong here
        var current = new Date().getTime();
        var expiresIn = (entity.getExpiresIn()  * 1000) + entity.getCreatedAt().getTime();

        if (current > expiresIn) return null;

        var basicAuth = new BasicAuthRS();

        basicAuth.setTokenType(entity.getTokenType());
        basicAuth.setAccessToken(entity.getAccessToken());
        basicAuth.setExpiresIn(entity.getExpiresIn());

        return basicAuth;

    }

}
