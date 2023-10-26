package com.everis.wishlist.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T getObject(String content, TypeReference<T> type) throws JsonProcessingException {
        return objectMapper.readValue(content, type);
    }
}
