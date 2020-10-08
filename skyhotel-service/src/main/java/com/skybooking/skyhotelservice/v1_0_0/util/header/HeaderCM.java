package com.skybooking.skyhotelservice.v1_0_0.util.header;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

@Component
public class HeaderCM {

    @Autowired
    private HttpServletRequest request;

    @PersistenceContext
    private EntityManager entityManager;

    public Long getCompanyId() {
        return (request.getHeader("X-CompanyId") != null ) ? (long) Integer.parseInt(request.getHeader("X-CompanyId")) : null;
    }

    public Long getCompanyIdZ() {
        return (request.getHeader("X-CompanyId") != null ) ? (long) Integer.parseInt(request.getHeader("X-CompanyId")) : 0;
    }

    public String skyType() {
        Long companyId = getCompanyIdZ();
        if (companyId == 0) {
            return "skyuser";
        }

        return "company";
    }

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

}
