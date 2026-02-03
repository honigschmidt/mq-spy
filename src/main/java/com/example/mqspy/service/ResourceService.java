package com.example.mqspy.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ResourceService {

    public JsonNode readJSON(String resource) {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
        try {
            return objectMapper.readTree(inputStream);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}
