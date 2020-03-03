package com.skybooking.skyflightservice.v1_0_0.util;

import org.apache.commons.lang.math.NumberUtils;

import java.util.Calendar;

public class GeneratorUtils {

    private static Calendar calendar = Calendar.getInstance();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Generate unique booking code of flight
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param lastCode
     * @return String
     */
    public static String bookingCode(String lastCode) {
        return getCode("SBFT", lastCode, "1");
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Get Generate unique booking code of flight
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param lastCode
     * @return String
     */
    public static String transactionCode(String lastCode) {
        return getCode("SBTP", lastCode, "1");
    }



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Unique code generator of SKYBOOKING
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param prefix
     * @param lastCode
     * @param type
     * @return String
     */
    private static String getCode(String prefix, String lastCode, String type) {

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

}
