package com.skybooking.skypointservice.v1_0_0.client.stakeholder.action;

import com.skybooking.skypointservice.config.AppConfig;
import com.skybooking.skypointservice.v1_0_0.client.ClientResponse;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.request.CompanyUserRQ;
import com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response.ClientResponseUserCompany;
import com.skybooking.skypointservice.v1_0_0.util.auth.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class StakeholderAction {
    @Autowired
    private WebClient webClient;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AuthUtility authUtility;

    public ClientResponse getUserReference(Integer stakeHolderUserId, Integer companyId) {

        if (companyId == 0) {
            return webClient.mutate()
                    .build()
                    .get()
                    .uri(appConfig.getStakeholderUrl() + appConfig.getStakeholderVersion() + "/auth/user-reference/" + stakeHolderUserId)
                    .retrieve()
                    .bodyToMono(ClientResponse.class)
                    .block();
        } else {
            return webClient.mutate()
                    .build()
                    .get()
                    .uri(appConfig.getStakeholderUrl() + appConfig.getStakeholderVersion() + "/auth/user-reference/" + stakeHolderUserId)
                    .header("X-CompanyId", companyId.toString())
                    .retrieve()
                    .bodyToMono(ClientResponse.class)
                    .block();
        }

    }

    public ClientResponse getCompanyDetail(String referenceCode) {
        return webClient.mutate()
                .build()
                .get()
                .uri(appConfig.getStakeholderUrl() + appConfig.getStakeholderVersion() + "/utils/company/" + referenceCode)
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .retrieve()
                .bodyToMono(ClientResponse.class)
                .block();
    }

    public ClientResponse getUserOrCompanyDetailByUserCode(String referenceCode) {
        return webClient.mutate()
                .build()
                .get()
                .uri(appConfig.getStakeholderUrl() + appConfig.getStakeholderVersion() + "/utils/company-user/" + referenceCode)
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .retrieve()
                .bodyToMono(ClientResponse.class)
                .block();
    }

    public ClientResponseUserCompany getUserOrCompanyListUserCode(List<String> keyCode) {

        CompanyUserRQ companyUserRQ = new CompanyUserRQ(keyCode);

        return webClient.mutate()
                .build()
                .post()
                .uri(appConfig.getStakeholderUrl() + appConfig.getStakeholderVersion() + "/utils/company-user")
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .bodyValue(companyUserRQ)
                .retrieve()
                .bodyToMono(ClientResponseUserCompany.class)
                .block();
    }

    public ClientResponse checkExistLang(String lang) {

        return webClient.mutate()
                .build()
                .get()
                .uri(appConfig.getStakeholderUrl() + appConfig.getStakeholderVersion() + "/utils/locale/exist/" + lang)
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .retrieve()
                .bodyToMono(ClientResponse.class)
                .block();
    }
}
