package com.cba.bookstore.utils;

import com.cba.bookstore.dto.BookResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class FeatureUtils {
    public static String URL = "http://localhost:9090";
    public static BookResponse getMockBookResponse() {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = new ClassPathResource("BookResponse.json");
        try {
            return objectMapper.readValue(resource.getInputStream(), BookResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
