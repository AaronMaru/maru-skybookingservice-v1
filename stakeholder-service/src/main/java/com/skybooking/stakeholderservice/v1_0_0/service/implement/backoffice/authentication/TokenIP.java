package com.skybooking.stakeholderservice.v1_0_0.service.implement.backoffice.authentication;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.OauthUserAccessTokenEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.OauthUserRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.backoffice.authentication.TokenSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.backoffice.authentication.TokenRQ;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.ResRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.backoffice.authentication.OauthTokenRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.backoffice.authentication.TokenRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.structure.StructureRS;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Service
public class TokenIP implements TokenSV {

    @Autowired private HttpServletRequest request;
    @Autowired private LocalizationBean localization;
    @Autowired private OauthUserRP oauthUserRP;

    @Override
    public StructureRS fetchToken(TokenRQ tokenRQ) {

        String baseUrl = String.format("%s://%s:%d/", request.getScheme(), request.getServerName(), request.getServerPort());
        RestTemplate restAPi = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        TokenRS tokenRS = new TokenRS();
        StructureRS structureRS = new StructureRS();

        try {

            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("Authorization", request.getHeader("Authorization"));
            map.add("grant_type", tokenRQ.getGrantType());
            HttpEntity<MultiValueMap<String, String>> requestBody = new HttpEntity<>(map, headers);

            OauthTokenRS oauthToken = restAPi.exchange(baseUrl + "oauth/token", HttpMethod.POST, requestBody, OauthTokenRS.class).getBody();

            if (oauthToken != null) {
                BeanUtils.copyProperties(oauthToken, tokenRS);
                ResRS response = localization.resAPI(HttpStatus.OK, "res_succ", tokenRS);

                structureRS.setStatus(response.getStatus());
                structureRS.setMessage(response.getMessage());
                structureRS.setData(response.getData());

                OauthUserAccessTokenEntity oauthUserAccessTokenEntity = new OauthUserAccessTokenEntity();
                oauthUserAccessTokenEntity.setJwtId(oauthToken.getJti());
                oauthUserAccessTokenEntity.setScopes(oauthToken.getScopes());
                oauthUserAccessTokenEntity.setName("SkyBooking Back Office");
                oauthUserAccessTokenEntity.setStatus(1);
                oauthUserRP.save(oauthUserAccessTokenEntity);
            } else {
                structureRS.setStatus(HttpStatus.UNAUTHORIZED.value());
                structureRS.setMessage("unauthorized");
            }

            return structureRS;

        } catch (Exception e) {
            ResRS response = localization.resAPI(HttpStatus.UNAUTHORIZED, "unauthorized", null);

            structureRS.setStatus(response.getStatus());
            structureRS.setMessage(response.getMessage());

            return structureRS;
        }
    }
}
