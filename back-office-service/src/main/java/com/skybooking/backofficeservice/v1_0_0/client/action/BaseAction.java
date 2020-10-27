package com.skybooking.backofficeservice.v1_0_0.client.action;

import com.skybooking.backofficeservice.config.AppConfig;
import com.skybooking.backofficeservice.v1_0_0.client.action.integration.DSTokenHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BaseAction {

    @Autowired protected DSTokenHolder dsTokenHolder;

    @Autowired protected AppConfig appConfig;

    @Autowired protected WebClient client;

    protected static final String TOKEN_TYPE = "Bearer ";

}
