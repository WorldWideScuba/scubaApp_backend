package com.scubaworld.scubagear;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

    @Autowired
    private ObjectMapper objectMapper;
    Logger logger = LoggerFactory.getLogger(JsonMapper.class);

    public String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("Error in JsonMapper.toJson():", e);
        }
        return null;
    }

    public <T> T toObject(String json, Class<T> JavaType) {
        try {
            return objectMapper.readValue(json, JavaType);
        } catch (Exception e) {
            logger.error("Error in JsonMapper.toObject()", e);
            return null;
        }
    }
}