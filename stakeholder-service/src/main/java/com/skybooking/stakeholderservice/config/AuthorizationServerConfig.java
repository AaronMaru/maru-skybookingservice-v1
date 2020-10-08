package com.skybooking.stakeholderservice.config;

import com.skybooking.stakeholderservice.constant.GrantTypeConstant;
import com.skybooking.stakeholderservice.security.CustomAccessTokenConverter;
import com.skybooking.stakeholderservice.security.CustomJWTEnhancer;
import com.skybooking.stakeholderservice.v1_0_0.service.implement.login.UserDetailsIP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomJWTEnhancer customJWTEnhancer;

    @Autowired
    Environment environment;

    @Autowired
    private UserDetailsIP userDetailsIP;


    @Bean
    @Qualifier("jwtAccessTokenConverter")
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(new CustomAccessTokenConverter());
        converter.setSigningKey(environment.getProperty("spring.jwtKey"));
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();
        return new JwtTokenStore(accessTokenConverter());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient(environment.getProperty("spring.oauth2.client-id"))
            .secret(bCryptPasswordEncoder.encode(environment.getProperty("spring.oauth2.client-secret")))
            .authorizedGrantTypes(GrantTypeConstant.PASSWORD, GrantTypeConstant.CLIENT_CREDENTIALS, GrantTypeConstant.REFRESH_TOKEN)
            .scopes("read", "write")
            .resourceIds("oauth2-resource", "skybooking-resource")
            .accessTokenValiditySeconds(604800)         // 7 days
            .refreshTokenValiditySeconds(604800 * 2)    // 14 days
            .and()
            .withClient(environment.getProperty("spring.back-office.client-id"))
            .secret(bCryptPasswordEncoder.encode(environment.getProperty("spring.back-office.client-secret")))
            .authorizedGrantTypes(GrantTypeConstant.CLIENT_CREDENTIALS, GrantTypeConstant.REFRESH_TOKEN)
            .scopes("create-additional-service-skyflight",
                "update-additional-service-skyflight",
                "delete-additional-service-skyflight",
                "list-additional-service-skyflight",
                "check-offline-booking-skyflight",
                "create-offline-booking-skyflight",
                "offline-topup-skypoint",
                "get-recent-offline-topup-skypoint",
                "get-recent-transaction-skypoint",
                "get-offline-topup-detail-skypoint",
                "search-offline-topup-skypoint",
                "transaction-summary-report-skypoint",
                "backend-dashboard-report-skypoint",
                "backend-account-info-report-skypoint",
                "topup-point-summary-report-skypoint",
                "topup-point-detail-report-skypoint",
                "spent-point-summary-report-skypoint",
                "spent-point-detail-report-skypoint",
                "earned-point-summary-report-skypoint",
                "earned-point-detail-report-skypoint",
                "refunded-point-summary-report-skypoint",
                "refunded-point-detail-report-skypoint",
                "upgraded-level-summary-report-skypoint",
                "summary-transaction-list-report-skypoint")
            .resourceIds("oauth2-resource", "skybooking-resource")
            .accessTokenValiditySeconds(604800)     // 7 days
            .refreshTokenValiditySeconds(2592000);  // 30 days
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(customJWTEnhancer, accessTokenConverter()));
        endpoints
                .tokenStore(tokenStore())
//                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(enhancerChain)
                .authenticationManager(authenticationManager).userDetailsService(userDetailsIP);
    }

    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.checkTokenAccess("permitAll()");
    }



}
