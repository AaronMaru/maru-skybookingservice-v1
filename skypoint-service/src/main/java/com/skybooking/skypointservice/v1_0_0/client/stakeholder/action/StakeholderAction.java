package com.skybooking.skypointservice.v1_0_0.client.stakeholder.action;

import com.skybooking.skypointservice.config.AppConfig;
import com.skybooking.skypointservice.v1_0_0.client.ClientResponse;
import com.skybooking.skypointservice.v1_0_0.util.auth.AuthUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;

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

    public ClientResponse getCompanyDetail(String referenceCode, HttpServletRequest httpServletRequest) {
        return webClient.mutate()
                .build()
                .get()
                .uri(appConfig.getStakeholderUrl() + appConfig.getStakeholderVersion() + "/utils/company/" + referenceCode)
                .header(HttpHeaders.AUTHORIZATION, authUtility.getAuthToken())
                .retrieve()
                .bodyToMono(ClientResponse.class)
                .block();


    }


}
