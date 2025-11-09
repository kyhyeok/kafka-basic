package com.example.emailsendproducer;

public record EmailSendMessage(
        String from,
        String to,
        String subject,
        String body
) {
}
