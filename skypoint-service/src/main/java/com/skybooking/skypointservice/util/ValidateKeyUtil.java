package com.skybooking.skypointservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public class ValidateKeyUtil {
    public static void validateKey(Object object, List<String> keyList) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            //======= Converting the Object to JSONString
            String jsonString = mapper.writeValueAsString(object);

            //======= Convert jsonStrong to JSONObject
            JSONObject json = new JSONObject(jsonString);

            keyList.forEach(key -> {
                if (json.isNull(key)) {
                    throw new BadRequestException("key_" + key + "_not_null", null);
                } else {
                    if (String.valueOf(json.get(key)).length() == 0) {
                        throw new BadRequestException("key_" + key + "_not_null", null);
                    }
                }

            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BadRequestException("validate_key_failed", null);
        }
    }

    public static void validateMultipartFile(List<MultipartFile> file, String key) {
        if (file == null) {
            throw new BadRequestException("key_" + key + "_not_null", null);
        } else {
            if (file.isEmpty()) {
                throw new BadRequestException("key_" + key + "_not_null", null);
            }
        }
    }

    public static void validatePhoneNumber(String number) {
        if (!number.matches("[0-9]+")) {
            throw new BadRequestException("phone_number_not_valid", null);
        } else if (number.length() < 6 || number.length() > 14) {
            throw new BadRequestException("phone_number_not_valid", null);
        }
    }

    public static void validateAmountNotNegative(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(0)) <= 0) {
            throw new BadRequestException("amount_not_negative", null);
        }
    }

    public static void validateEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (!email.matches(regex)) {
            throw new BadRequestException("email_not_valid", null);
        }
    }

    public static void validatePhoneCode(String phoneCode) {
        if (!phoneCode.substring(0, 1).equalsIgnoreCase("+") ||
                !phoneCode.substring(1, phoneCode.length() - 1).matches("[0-9]+") ||
                phoneCode.length() < 2 || phoneCode.length() > 6) {
            throw new BadRequestException("phone_code_not_valid", null);
        }
    }
}
