package com.skybooking.skyflightservice.v1_0_0.security.token;

import com.skybooking.skyflightservice.config.AppConfig;
import com.skybooking.skyflightservice.v1_0_0.io.entity.token.ServiceTokenEntity;
import com.skybooking.skyflightservice.v1_0_0.io.repository.token.ServiceTokenRP;
import com.skybooking.skyflightservice.v1_0_0.ui.model.response.BasicAuthRS;
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
    final static String FLIGHT_SERVICE = "flight";

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private ServiceTokenRP serviceTokenRP;


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
        headers.setBasicAuth(appConfig.getDISTRIBUTED_USER(), appConfig.getDISTRIBUTED_SECRET());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // add posting data
        var data = new LinkedMultiValueMap<String, String>();
        data.add("grant_type", "client_credentials");

        var request = new HttpEntity<MultiValueMap<String, String>>(data, headers);

        // request access token
        var rest = new RestTemplate();
        var url = appConfig.getDISTRIBUTED_URI() + "/secure/" + appConfig.getDISTRIBUTED_VERSION() + "/oauth/token";

        var response = rest.postForEntity(url, request, BasicAuthRS.class);

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
        entity.setSupplier(DSTokenHolder.DISTRIBUTED_SUPPLIER);
        entity.setService(DSTokenHolder.FLIGHT_SERVICE);
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

        var entity = serviceTokenRP.findLastAccessToken(appConfig.getAPP_ID(), DSTokenHolder.DISTRIBUTED_SUPPLIER, DSTokenHolder.FLIGHT_SERVICE);
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
