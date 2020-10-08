package com.skybooking.skygatewayservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.skybooking.skygatewayservice.io.entity.FrontendLocaleEntity;
import com.skybooking.skygatewayservice.io.entity.FrontendTranslationEntity;
import com.skybooking.skygatewayservice.io.repository.FrontendLocaleRP;
import com.skybooking.skygatewayservice.io.repository.FrontendTranslationRP;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class TranslationService {

    private static final Logger logger = LoggerFactory.getLogger(TranslationService.class.getName());
    private static final String LOCALE = "en";
    private final FrontendLocaleRP frontendLocaleRP;
    private final FrontendTranslationRP frontendTranslationRP;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * exchange message translation
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @throws ZuulException Exception
     */
    public void message() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();

        try
        {
            InputStream responseDataStream = context.getResponseDataStream();

            if(responseDataStream != null) {

                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNodeRS = mapper.readTree(new InputStreamReader(responseDataStream, StandardCharsets.UTF_8));

                if (jsonNodeRS != null) {
                    logger.info("RESPONSE BODY BEFORE MESSAGE TRANSLATION: {}", jsonNodeRS.toString());
                    String message = "";
                    if (jsonNodeRS.has("message")) {
                        HttpServletRequest request = context.getRequest();
                        String messageKey = jsonNodeRS.get("message").asText();

                        if (request.getHeader("X-Localization") != null)
                            message = getMessage(messageKey, getLocaleId(request.getHeader("X-Localization")));
                        else
                            message = getMessage(messageKey, getLocaleId(LOCALE));
                    }

                    ((ObjectNode)jsonNodeRS).put("message", message);
                    logger.info("RESPONSE BODY AFTER MESSAGE TRANSLATION: {}", jsonNodeRS.toString());

                    InputStream response = new ByteArrayInputStream(jsonNodeRS.toString().getBytes());

                    context.setResponseDataStream(response);
                } else {
                    logger.info("RESPONSE BODY: {}", "");
                }
            } else {
                logger.info("RESPONSE BODY: {}", "");
            }
        }
        catch (Exception e)
        {
            throw new ZuulException(e, INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get locale ID by locale
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param locale String
     * @return Integer
     */
    private Integer getLocaleId(String locale) {
        FrontendLocaleEntity localeEntity = frontendLocaleRP.available(locale.toLowerCase());
        return localeEntity != null ? localeEntity.getId() : frontendLocaleRP.first().getId();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get message translation
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param messageKey String
     * @param localeId Integer
     * @return String
     */
    private String getMessage(String messageKey, Integer localeId) {
        FrontendTranslationEntity translationEntity = frontendTranslationRP.first(messageKey, localeId);

        if (translationEntity != null) {
            return translationEntity.getValue();
        }

        FrontendTranslationEntity defaultTranslation = frontendTranslationRP.first(messageKey, getLocaleId(LOCALE));
        return defaultTranslation != null ? defaultTranslation.getValue() : messageKey;
    }
}
