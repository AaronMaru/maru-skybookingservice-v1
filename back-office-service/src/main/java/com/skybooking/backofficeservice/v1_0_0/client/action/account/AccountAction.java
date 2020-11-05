package com.skybooking.backofficeservice.v1_0_0.client.action.account;

import com.skybooking.backofficeservice.constant.EndpointConstant;
import com.skybooking.backofficeservice.v1_0_0.client.action.BaseAction;
import com.skybooking.backofficeservice.v1_0_0.client.model.response.account.AccountStructureServiceRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AccountAction extends BaseAction {

    @Autowired
    private HttpServletRequest httpServletRequest;

    public AccountStructureServiceRS getAccountDetail(){
        try {
            return client
                .mutate()
                .build()
                .get()
                .uri(appConfig.getSKY_URL() + EndpointConstant.AccountProfileDetail.V1_0_0)
                .header(HttpHeaders.AUTHORIZATION, httpServletRequest.getHeader("Authorization"))
                .retrieve()
                .bodyToMono(AccountStructureServiceRS.class)
                .block();

        }catch (Exception exception){
            throw exception;
        }

    }
}
