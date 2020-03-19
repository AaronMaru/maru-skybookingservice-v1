package com.skybooking.skyhistoryservice.v1_0_0.util.header;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

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
                .setParameter("locale", request.getHeader("X-localization"))
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
     * @Return id of localization
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

//        if (!companyId.equals(cId)) {
//            throw new BadRequestException("sth_w_w", null);
//        }

        return "company";
    }


}
