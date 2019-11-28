package com.skybooking.skyhistoryservice.v1_0_0.util.calculator;

import antlr.StringUtils;

public class Calculator {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Trim amount
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param amount
     * @Param int
     */
    public double trimAmount(double amount) {

        double myAmount = amount * 1000;
        int amountInt = (int) myAmount;
        String lastNum = Integer.toString(amountInt);
        lastNum = lastNum.substring(lastNum.length() -1);
        amountInt = Integer.parseInt(lastNum) < 1 ? amountInt : amountInt + 10;
        myAmount = amountInt/10;

        return myAmount/100;

    }

}
