package com.skybooking.skyflightservice.v1_0_0.util.header;

import com.skybooking.skyflightservice.v1_0_0.util.classes.SkyHeader;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class HeaderBean {

    @Autowired
    private HttpServletRequest request;

    @PersistenceContext
    private EntityManager entityManager;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Multi language response message
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param key
     * @Return String
     */
    public String getLocalization() {

        Object result = entityManager
            .createNativeQuery("SELECT CASE WHEN COUNT(name) > 0 THEN 'true' ELSE 'false' END FROM frontend_locales WHERE locale = :locale AND status = 1")
            .setParameter("locale", request.getHeader("X-Localization"))
            .getSingleResult();

        Boolean b = Boolean.parseBoolean(result.toString());

        String reqLocale = request.getHeader("X-localization");
        String locale = reqLocale == null ? "en" : (reqLocale.equals("")) ? "en" : (b ? reqLocale : "en");

        return locale;
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
            .setParameter("currency", request.getHeader("X-Currency-Code"))
            .getSingleResult();

        Boolean b = Boolean.parseBoolean(result.toString());

        String reqCurrency = request.getHeader("X-Currency-Code");
        String currency = reqCurrency == null ? "USD" : (reqCurrency.equals("")) ? "USD" : (b ? reqCurrency : "USD");

        return currency;
    }

    public SkyHeader getHeaders() {
        var header = new SkyHeader();

        header.setLocalization(this.getLocalization());
        header.setOriginatingIp(request.getRemoteAddr());
        header.setCurrencyCode(this.getCurrencyCode());


        String companyId = request.getHeader("X-CompanyId");

        if (companyId != null) {

            String regex = "^[0-9]+$";
            Pattern pattern = Pattern.compile(regex);

            if (pattern.matcher(companyId).matches()) {
                header.setCompanyId(Integer.parseInt(companyId));
            }

        }

        if (request.getHeader("X-PlayerId") != null) {
            header.setPlayerId(request.getHeader("X-PlayerId"));
        }
        if (request.getHeader("X-User-Agents") != null) {
            header.setUserAgents(request.getHeader("X-User-Agents"));
        }
        if (request.getHeader("X-DeviceId") != null) {
            header.setDeviceId(request.getHeader("X-DeviceId"));
        }
        if (request.getHeader("X-Current-Timezone") != null) {
            header.setDeviceId(request.getHeader("X-Current-Timezone"));
        }

        return header;
    }


    public Map<String, Object> getSkyownerHeaderMissing() {

        Map<String, Object> headers = new LinkedHashMap<>();

        if (request.getHeader("X-CompanyId") == null) {
            headers.put("X-CompanyId", "X-CompanyId is required");
        }

        return headers;
    }

    public String getPlayerId() {
        return request.getHeader("X-PlayerId") != null && !request.getHeader("X-PlayerId").isEmpty() ? request.getHeader("X-PlayerId") : "";
    }

}
