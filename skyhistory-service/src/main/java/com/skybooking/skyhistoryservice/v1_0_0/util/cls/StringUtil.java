package com.skybooking.skyhistoryservice.v1_0_0.util.cls;

public class StringUtil {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * convert from camel case to snake
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param camelCase
     * @return String
     */
    public static String toSnake(String camelCase) {

        return camelCase.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
