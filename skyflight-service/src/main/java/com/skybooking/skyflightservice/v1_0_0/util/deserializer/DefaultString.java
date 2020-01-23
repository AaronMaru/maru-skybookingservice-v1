package com.skybooking.skyflightservice.v1_0_0.util.deserializer;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;

public class DefaultString extends JsonDeserializer<String> {


    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return jsonParser.getText().toLowerCase();
    }

    @Override
    public String getNullValue(DeserializationContext ctxt) throws JsonMappingException {
        return "";
    }
}
