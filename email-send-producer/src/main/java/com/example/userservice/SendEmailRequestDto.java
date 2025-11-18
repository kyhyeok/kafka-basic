package com.example.userservice;

public record SendEmailRequestDto(
        String from,
        String to,
        String subject,
        String body
) {
}
