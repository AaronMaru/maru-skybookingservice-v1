package com.skybooking.skyflightservice.v1_0_0.util.calculator;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatter {

    private static NumberFormat numberFormat() {
        var formatter = NumberFormat.getInstance(new Locale("en"));
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setGroupingUsed(false);
        formatter.setRoundingMode(RoundingMode.HALF_UP);

        return formatter;
    }

    public static Double amount(Double n) {
        return Double.parseDouble(numberFormat().format(n));
    }

    /**
     * TODO
     */
//    public static BigDecimal amount(BigDecimal n) {
//        return BigDecimal.valueOf(numberFormat().format(n));
//    }
}
