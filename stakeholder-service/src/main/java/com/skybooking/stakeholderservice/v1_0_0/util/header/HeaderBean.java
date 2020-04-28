package com.skybooking.stakeholderservice.v1_0_0.util.header;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.exception.httpstatus.UnauthorizedException;
import com.skybooking.stakeholderservice.v1_0_0.util.general.ApiBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeaderBean {

    @Autowired
    private HttpServletRequest request;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ApiBean apiBean;



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Multi language response message
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param key
     * @Return String
     */
    public String getLocalization() {

        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String lang = request.getHeader("X-localization");

        //This condition for Language module API
        if (pathVariables.containsKey("lang")) {
            lang = pathVariables.get("lang").toString();
        }

        Object result = entityManager
                .createNativeQuery("SELECT CASE WHEN COUNT(name) > 0 THEN 'true' ELSE 'false' END FROM frontend_locales WHERE locale = :locale AND status = 1")
                .setParameter("locale", lang)
                .getSingleResult();

        Boolean b = Boolean.parseBoolean(result.toString());

        lang = lang == null ? "en" : (lang.equals("")) ? "en" : (b ? lang : "en");

        return lang;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get localization id by locale name
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @return id of localization
     */
    public long getLocalizationId() {

        Object result = entityManager
                .createNativeQuery("SELECT id FROM frontend_locales WHERE locale = :locale AND status = 1")
                .setParameter("locale", this.getLocalization())
                .getSingleResult();

        return Long.valueOf(result.toString());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get currency
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param key
     * @Return String
     */
    public String getCurrencyCode() {

        Object result = entityManager
                .createNativeQuery("SELECT CASE WHEN COUNT(code) > 0 THEN 'true' ELSE 'false' END FROM currency WHERE code = :currency AND status = 1")
                .setParameter("currency", request.getHeader("CurrencyCode"))
                .getSingleResult();

        Boolean b = Boolean.parseBoolean(result.toString());

        String reqCurrency = request.getHeader("CurrencyCode");
        String currency = reqCurrency == null ? "USD" : (reqCurrency.equals("")) ? "USD" : (b ? reqCurrency : "USD");

        return currency;

    }


    public String getCompanyId(Long cId) {

        Long companyId = (request.getHeader("X-CompanyId") != null && !request.getHeader("X-CompanyId").isEmpty())
                ? Long.valueOf(request.getHeader("X-CompanyId"))
                : 0;
        if (companyId == 0) {
            return "skyuser";
        }

        return "company";
    }

    public String getPlayerId() {
        return request.getHeader("X-PlayerId") != null && !request.getHeader("X-PlayerId").isEmpty() ? request.getHeader("X-PlayerId") : "";
    }

    public HashMap<String, String> getUserAgent() {

        String userAgent = request.getHeader("X-User-Agents");
        if (userAgent == null || userAgent.isEmpty()) {
            throw new UnauthorizedException("unauthorized", null);
        }

        String platform = apiBean.getPlatform();
        String[] deviceApp = new String[]{"ios", "android"};
        List<String> deviceAppList = Arrays.asList(deviceApp);

        String device = userAgent;

        if (platform.equals("mobile")) {
            if (!deviceAppList.contains(userAgent)) {
                throw new UnauthorizedException("Please provide a specific user agent", null);
            }
        }

        HashMap<String, String> userArgentMap = new HashMap<>();
        userArgentMap.put("from", platform);
        userArgentMap.put("device", device);

        return userArgentMap;
    }


}
