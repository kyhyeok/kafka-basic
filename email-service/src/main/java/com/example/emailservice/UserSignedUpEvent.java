package com.example.emailservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserSignedUpEvent {
    private Long userId;
    private String email;
    private String name;

    public UserSignedUpEvent() {
    }

    public UserSignedUpEvent(Long userId, String email, String name) {
        this.userId = userId;
        this.email = email;
        this.name = name;
    }

    public static UserSignedUpEvent fromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, UserSignedUpEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json 파싱 실패");
        }
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
