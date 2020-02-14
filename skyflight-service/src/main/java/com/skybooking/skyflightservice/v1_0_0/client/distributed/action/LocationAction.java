package com.skybooking.skyflightservice.v1_0_0.client.distributed.action;


import com.skybooking.skyflightservice.config.AppConfig;
import com.skybooking.skyflightservice.constant.CustomHeaderConstant;
import com.skybooking.skyflightservice.v1_0_0.security.token.DSTokenHolder;
import com.skybooking.skyflightservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class LocationAction {

    @Autowired
    private DSTokenHolder dsTokenHolder;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private HeaderBean headerBean;

    @Autowired
    private WebClient client;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Quick Search Action
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param groupByKey
     * @return
     */
    public Mono<Object> getQuickSearchAction(String groupByKey) {

        return client
                .mutate()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                }).build())
                .build()
                .get()
                .uri(appConfig.getDISTRIBUTED_URI() + "/utils/" + appConfig.getDISTRIBUTED_VERSION() + "/sb-quick-search-location?groupByKey=" + groupByKey)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                .header(CustomHeaderConstant.LOCALIZATION, headerBean.getLocalization())
                .header(CustomHeaderConstant.CURRENCY, headerBean.getCurrencyCode())
                .retrieve()
                .bodyToMono(Object.class);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Autocomplete Search Action
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param keyword
     * @param groupBy
     * @return
     */
    public Mono<Object> getAutoCompleteAction(String keyword, Boolean groupBy) {

        return client
                .mutate()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                }).build())
                .build()
                .get()
                .uri(appConfig.getDISTRIBUTED_URI() + "/utils/" + appConfig.getDISTRIBUTED_VERSION() + "/sb-auto-complete?keyword=" + keyword + "&groupBy=" + groupBy)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                .header(CustomHeaderConstant.LOCALIZATION, headerBean.getLocalization())
                .header(CustomHeaderConstant.CURRENCY, headerBean.getCurrencyCode())
                .retrieve()
                .bodyToMono(Object.class);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Countries
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return
     */
    public Mono<Object> getCountryAction() {

        return client
                .mutate()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                }).build())
                .build()
                .get()
                .uri(appConfig.getDISTRIBUTED_URI() + "/utils/" + appConfig.getDISTRIBUTED_VERSION() + "/sb-countries")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                .header(CustomHeaderConstant.LOCALIZATION, headerBean.getLocalization())
                .header(CustomHeaderConstant.CURRENCY, headerBean.getCurrencyCode())
                .retrieve()
                .bodyToMono(Object.class);

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Cities
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param countryId
     * @return
     */
    public Mono<Object> getCityAction(int countryId) {

        return client
                .mutate()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                }).build())
                .build()
                .get()
                .uri(appConfig.getDISTRIBUTED_URI() + "/utils/" + appConfig.getDISTRIBUTED_VERSION() + "/sb-cities/" + countryId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + dsTokenHolder.getAuth().getAccessToken())
                .header(CustomHeaderConstant.LOCALIZATION, headerBean.getLocalization())
                .header(CustomHeaderConstant.CURRENCY, headerBean.getCurrencyCode())
                .retrieve()
                .bodyToMono(Object.class);

    }

}
