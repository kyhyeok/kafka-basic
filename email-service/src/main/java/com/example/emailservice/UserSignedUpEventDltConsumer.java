package com.example.emailservice;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserSignedUpEventDltConsumer {

    @KafkaListener(
            topics = "user.signed-up.dlt",
            groupId = "email-service"
    )
    public void consume(String message) {
        // 르그 시스템에 전송
        System.out.println("로그 시스템에 전송: " + message);

        // 알림 발송 (슬랙, 디스코드, 텔레그램 등등)
        System.out.println("알림 발송: " + message);
    }
}
