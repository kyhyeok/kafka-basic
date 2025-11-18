package com.example.userservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public EmailService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmail(SendEmailRequestDto request) {
        EmailSendMessage emailSendMessage = new EmailSendMessage(
                request.from(), request.to(), request.subject(), request.body()
        );

        this.kafkaTemplate.send("email.send", toJsonString(emailSendMessage));
    }

    private String toJsonString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json 직렬화 실패");
        }
    }
}
