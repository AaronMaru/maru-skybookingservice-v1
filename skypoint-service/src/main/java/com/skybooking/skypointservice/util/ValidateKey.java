package com.skybooking.skypointservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skybooking.skypointservice.httpstatus.BadRequestException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ValidateKey {
    public static void validateKey(Object object, List<String> keyList) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            //======= Converting the Object to JSONString
            String jsonString = mapper.writeValueAsString(object);

            //======= Convert jsonStrong to JSONObject
            JSONObject json = new JSONObject(jsonString);

            keyList.forEach(key -> {
                if (json.isNull(key)) {
                    throw new BadRequestException("Key " + key + " can't be blank.", null);
                } else {
                    if (String.valueOf(json.get(key)).length() == 0) {
                        throw new BadRequestException("Key " + key + " can't be blank.", null);
                    }
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BadRequestException("Validate key failed.", null);
        }
    }

    public static void validateMultipartFile(MultipartFile file, String key) {
        if (file == null) {
            throw new BadRequestException("Key " + key + " can't be blank.", null);
        } else {
            if (file.isEmpty()) {
                throw new BadRequestException("Key " + key + " can't be blank.", null);
            }
        }
    }
}
