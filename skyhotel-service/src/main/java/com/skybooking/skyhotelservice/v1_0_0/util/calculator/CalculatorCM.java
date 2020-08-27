package com.skybooking.skyhotelservice.v1_0_0.util.calculator;

import com.skybooking.skyhotelservice.v1_0_0.io.nativeQuery.shopping.ShoppingNQ;
import com.skybooking.skyhotelservice.v1_0_0.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Component
public class CalculatorCM {

    @Autowired
    private ShoppingNQ shoppingNQ;

    @Autowired
    private JwtUtils jwt;

    @Autowired
    private HttpServletRequest httpServletRequest;

    public BigDecimal markupPrice(BigDecimal amount) {
        BigDecimal percentage = shoppingNQ.hotelMarkup(amount, this.userType()).getPercentage();
        return NumberFormatter.trimAmount(amount.multiply(percentage).add(amount));
    }

    public BigDecimal markupPrice(double amount) {
        var totalAmount = NumberFormatter.trimAmount(amount);
        var percentage = NumberFormatter.trimAmount(shoppingNQ.hotelMarkup(totalAmount, this.userType()).getPercentage());
        return NumberFormatter.trimAmount(totalAmount.multiply(percentage).add(totalAmount));
    }

    private String userType() {

        String userType = "SKYUSER";

        if (httpServletRequest.getHeader("Authorization") != null) {
            userType = jwt.userToken().getUserType();
        }

        return userType.toUpperCase();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get booking total markup amount
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param totalAmount
     * @param percentage
     * @return double
     */
    public BigDecimal getAmountPercentage(BigDecimal totalAmount, BigDecimal percentage) {
        return NumberFormatter.trimAmount(totalAmount.multiply(percentage));
    }
    public double getBookingTotalMarkupAmount(double totalAmount, double percentage) {
        return NumberFormatter.amount(totalAmount * percentage);
    }

}
