package com.skybooking.skypointservice.util;

import org.apache.commons.lang.math.NumberUtils;

import java.util.Calendar;

public class GenerateTransactionCode {
    public static Calendar calendar = Calendar.getInstance();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Create transactionCode
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @Param prefix
     * @Param lastCode
     */
    public static String generateTransactionCodeUnique(String prefix, String lastCode, String type) {

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

        String firstCode = lastCode.substring(0, 6);
        String numUser = lastCode.substring(lastCode.length() - 6).substring(0, 4);
        String numIncr = lastCode.substring(prefix.length(), prefix.length() + 1);

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
}
