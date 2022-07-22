package com.app.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonConverter {

    private static JsonConverter jsonConverter = new JsonConverter();
    private ObjectMapper objectMapper;
    private TypeFactory typeFactory;

    private JsonConverter() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);

        typeFactory = TypeFactory.defaultInstance();
    }

    public static JsonConverter getInstance() {
        return jsonConverter;
    }

    public String object2Json(Object object) {
        String result = null;
        try {
            result = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public <T> T json2Object(Object jsonObject, Class<T> clazz) {
        T result = null;
        try {
            result = objectMapper.readValue(jsonObject.toString(), clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

}