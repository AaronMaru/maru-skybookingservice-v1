package com.skybooking.stakeholderservice.v1_0_0.util.general;

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


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * convert from snake case to camel
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param snakeCase
     * @return String
     */
    public static String toCamel(String snakeCase) {

        StringBuilder stringBuilder = new StringBuilder(snakeCase);
        for (int i = 0; i < stringBuilder.length(); i++) {
            if (stringBuilder.charAt(i) == '_') {
                stringBuilder.deleteCharAt(i);
                stringBuilder.replace(i, i + 1, String.valueOf(Character.toUpperCase(stringBuilder.charAt(i))));
            }
        }

        return stringBuilder.toString();
    }


}
