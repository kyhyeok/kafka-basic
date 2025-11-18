package com.example.userservice;

public record EmailSendMessage(
        String from,
        String to,
        String subject,
        String body
) {
}
