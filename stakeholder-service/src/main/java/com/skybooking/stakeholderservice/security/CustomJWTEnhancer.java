package com.skybooking.stakeholderservice.security;

import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyDocRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyHasUserRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.company.CompanyRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.users.UserRepository;
import com.skybooking.stakeholderservice.v1_0_0.service.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Configurable
@Component
public class CustomJWTEnhancer implements TokenEnhancer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyHasUserRP companyHasUserRP;

    @Autowired
    private CompanyRP companyRP;

    @Autowired
    private CompanyDocRP companyDocRP;

    @Autowired
    private Environment environment;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        if (authentication.getName().equals(environment.getProperty("spring.back-office.client-id")))
            return accessToken;

        Map<String, Object> additionalInfo = new HashMap<>();

        var userPrinciple = (UserPrinciple) authentication.getPrincipal();
        var user = userRepository.findById(userPrinciple.getId());
        var company = companyHasUserRP.findByStakeholderUserId(user.getStakeHolderUser().getId());
        additionalInfo.put("userId", user.getId());
        additionalInfo.put("stakeholderId", user.getStakeHolderUser().getId());
        additionalInfo.put("fullName", user.getStakeHolderUser().getLastName() + " " + user.getStakeHolderUser().getFirstName());
        additionalInfo.put("userType", "skyuser");

        if (company != null) {

            var companyDetail = companyRP.findById(company.getStakeholderCompanyId());

            additionalInfo.put("companyId", company.getStakeholderCompanyId());
            additionalInfo.put("userRole", company.getSkyuserRole());

            String profile = companyDetail.get().getProfileImg() == null ? "default.png" : companyDetail.get().getProfileImg();
            var profileItenaryEntity = companyDocRP.findByStakeholderCompanyAndType(companyDetail.get(), "itenery");

            String profileItenery = (profileItenaryEntity.size() == 0) ? "default.png" : profileItenaryEntity.get(0).getFileName();

            additionalInfo.put("profile", environment.getProperty("spring.awsImageUrl.companyProfile") + "medium/" + profile);
            additionalInfo.put("profileItinerary", environment.getProperty("spring.awsImageUrl.companyProfile") + "origin/" + profileItenery);
            additionalInfo.put("userType", company.getStatus() == 1 ? "skyowner" : "skystaff");
        }



        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;

    }


}
