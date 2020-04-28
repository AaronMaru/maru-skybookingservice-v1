package com.skybooking.skyflightservice.v1_0_0.util.shopping;

import com.skybooking.skyflightservice.constant.UserConstant;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.MarkupNQ;
import com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping.MarkupTO;
import com.skybooking.skyflightservice.v1_0_0.service.model.security.UserAuthenticationMetaTA;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.CalculatorUtils;
import com.skybooking.skyflightservice.v1_0_0.util.calculator.NumberFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ShoppingUtils {

    @Autowired
    private MarkupNQ markupNQ;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get user markup price
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param user
     * @param userId
     * @param classType
     * @return MarkupTO
     */
    public MarkupTO getUserMarkupPrice(String user, Integer userId, String classType) {

        var markUp = new MarkupTO();

        if (UserConstant.SKYUSER.equalsIgnoreCase(user)) {

            markUp = markupNQ.getMarkupPriceSkyUser(userId, classType.toUpperCase());

            if (markUp == null || markUp.getMarkup() == null) {
                markUp = markupNQ.getMarkupPriceAnonymousUser(classType.toUpperCase(), UserConstant.SKYUSER);
            }

        } else if (UserConstant.SKYOWNER.equalsIgnoreCase(user)) {

            markUp = markupNQ.getMarkupPriceSkyOwnerUser(userId, classType.toUpperCase());

            if (markUp == null || markUp.getMarkup() == null) {
                markUp = markupNQ.getMarkupPriceAnonymousUser(classType.toUpperCase(), UserConstant.SKYOWNER);
            }

        } else {
            markUp = markupNQ.getMarkupPriceAnonymousUser(classType.toUpperCase(), UserConstant.SKYUSER);
        }

        return markUp;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get user mark up price
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param authenticationMeta
     * @param classType
     * @return MarkupTO
     */
    public MarkupTO getUserMarkupPrice(UserAuthenticationMetaTA authenticationMeta, String classType) {

        if (authenticationMeta.getUserType().equalsIgnoreCase(UserConstant.SKYOWNER)) {
            return this.getUserMarkupPrice(UserConstant.SKYOWNER, authenticationMeta.getCompanyId(), classType.toUpperCase());
        }

        return this.getUserMarkupPrice(authenticationMeta.getUserType(), authenticationMeta.getStakeholderId(), classType.toUpperCase());
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * apply payment method markup price
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param paymentMethodPercent
     * @param total
     * @return BigDecimal
     */
    public BigDecimal getMarkupPriceAndApplyPaymentMethodPercentage(double paymentMethodPercent, BigDecimal total) {
        return total.add(CalculatorUtils.getAmountPercentage(total, BigDecimal.valueOf(paymentMethodPercent)));
    }

}
