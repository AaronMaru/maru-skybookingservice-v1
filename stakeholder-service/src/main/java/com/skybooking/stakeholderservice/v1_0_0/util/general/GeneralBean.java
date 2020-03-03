package com.skybooking.stakeholderservice.v1_0_0.util.general;

import com.skybooking.stakeholderservice.exception.httpstatus.BadRequestException;
import com.skybooking.stakeholderservice.exception.httpstatus.GoneException;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.verify.VerifyUserEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.verify.VerifyUserRP;
import com.skybooking.stakeholderservice.v1_0_0.util.localization.LocalizationBean;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.beans.FeatureDescriptor;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

public class GeneralBean {

    public Calendar calendar = Calendar.getInstance();

    @Autowired
    private VerifyUserRP verifyUserRP;

    @Autowired
    private LocalizationBean localization;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Check string or empty string
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Return String
     */
    public String strCv(String str) {
        return (str == null) ? "" : str;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Create user unique code SKYU01000119
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param prefix
     * @Param lastCode
     */
    public String stakeUniqueCode(String prefix, String lastCode, String type) {

        String newYear = Integer.toString(calendar.get(Calendar.YEAR)).substring(2, 4);

        /**
         * Set first unique code when null
         */
        if (lastCode == null) {
            return prefix + "0" + type + "0001" + newYear;
        }

        String currentYear = lastCode.substring(lastCode.length() - 2).substring(0, 2);

        /**
         * Set first unique code for new year
         */
        if (!currentYear.equals(newYear)) {
            return prefix + "0" + type + "0001" + newYear;
        }

        String firstCode = lastCode.substring(0, 6);//Ex: SKYU01
        String numUser = lastCode.substring(lastCode.length() - 6).substring(0, 4); //Ex: 0001
        String numIncr = lastCode.substring(prefix.length(), prefix.length() + 1);//Ex: SKYU71000119 => 7

        /**
         * Check 4 digits when equall = 9999
         * This 4 digits will reset to 0001
         */
        if (numUser.equals("9999")) {

            numUser = "0001";
            if (numIncr.equals("9")) {

                return prefix + "A" + type + numUser + currentYear;

            } else if (!NumberUtils.isNumber(numIncr)) {

                return prefix + (char) (numIncr.charAt(0) + 1) + type + numUser + currentYear;

            } else {

                return prefix + (NumberUtils.toInt(numIncr) + 1) + type + numUser + currentYear;

            }

        } else {

            String numInc = Integer.toString(NumberUtils.toInt(numUser) + 1);
            return firstCode + "0000".substring(numInc.length()) + numInc + currentYear;

        }


    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get property name of object that contain null value
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param object
     * @return String[]
     */
    public String[] getNullPropertyNames(Object object) {

        final BeanWrapper wrappedSource = new BeanWrapperImpl(object);

        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Set login expiration
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param verifyUserEntity
     */
    public void expiredInvalidToken(VerifyUserEntity verifyUserEntity, String token) {

        if (verifyUserEntity == null) {
            throw new BadRequestException("vf_not_match", token);
        }

        Date d = new Date();
        Timestamp ts = new Timestamp(d.getTime());

        long diff = ts.getTime() - verifyUserEntity.getCreatedAt().getTime();

        int diffMin = (int) (diff / (60 * 1000));

        if (diffMin > 3) {
            throw new GoneException("vf_expired", "");
        }

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Set expire request
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param userEntity
     * @Param status
     */
    public void expireRequest(UserEntity userEntity, Integer status) {
        List<VerifyUserEntity> verify = verifyUserRP.findByUserId(userEntity.getId(), status);
        setExpireTime(verify);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Set expire request for mobile app
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param username
     */
    public void expireRequestMobile(String username) {
        List<VerifyUserEntity> verify = verifyUserRP.findByUsername(username);
        setExpireTime(verify);
    }

    public void setExpireTime(List<VerifyUserEntity> verify) {
        Date d = new Date();
        Timestamp ts = new Timestamp(d.getTime());

        if (!verify.isEmpty()) {
            VerifyUserEntity verifyLast = verify.stream().skip(verify.size() - 1).findFirst().get();
            long diff = ts.getTime() - verifyLast.getCreatedAt().getTime();

            int diffMin = (int) (diff / (60 * 1000));

            if ((verify.size() % 5) == 0 && diffMin < 60) {
                throw new BadRequestException("sent_vf_limit", "");
            }
        }
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Errors validation for form data
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param verifyUserEntity
     */
    public Object errors(Errors errors) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").format(new Date()));
        body.put("status", 400);

        List<FieldError> fieldErrors = errors.getFieldErrors();
        String validation = "";

        for (FieldError fieldError : fieldErrors) {
            validation = localization.multiLanguageRes(fieldError.getDefaultMessage());
        }

        body.put("message", validation);
        body.put("data", "");

        return body;

    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Validation file size
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param verifyUserEntity
     */
    public void errorMultipart(MultipartFile multipartFile) {
        if (multipartFile != null && multipartFile.getSize() > 6000000) {
            throw new BadRequestException("Oop the file size to large", "");
        }
    }


}
