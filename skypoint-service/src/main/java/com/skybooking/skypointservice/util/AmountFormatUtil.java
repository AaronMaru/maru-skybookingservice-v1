package com.skybooking.skypointservice.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountFormatUtil {
    public static BigDecimal roundAmount(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    public static void main(String[] strings) {
        System.out.println(roundAmount(BigDecimal.valueOf(0.124)));
    }
}
