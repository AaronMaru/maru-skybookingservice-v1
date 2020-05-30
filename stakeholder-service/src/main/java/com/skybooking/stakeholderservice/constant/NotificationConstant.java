package com.skybooking.stakeholderservice.constant;

import java.util.HashMap;

public class NotificationConstant {

    public static final String ADD_SKYUSER = "ADD_SKYUSER";
    public static final String INVITE = "INVITE";

    //This for replace string
    public static final String FULL_NAME = "fullName";
    public static final String CURRENT_ROLE = "currentRole";
    public static final String NEW_ROLE = "newRole";
    public static final String DATE_TIME = "dateTime";
    public static final String COMPANY_NAME = "companyName";

    public static final HashMap<String, String> STRING_REPLACE = new HashMap<>() {{
        put(FULL_NAME, "{{FULL_NAME}}");
        put(CURRENT_ROLE, "{{CURRENT_ROLE}}");
        put(NEW_ROLE, "{{NEW_ROLE}}");
        put(DATE_TIME, "{{DATE_TIME}}");
        put(COMPANY_NAME, "{{company_name}}");
    }};
    //End

}
