package com.example.emailsendproducer;

public record SendEmailRequestDto(
        String from,
        String to,
        String subject,
        String body
) {
}
