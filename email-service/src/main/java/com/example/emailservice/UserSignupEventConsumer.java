package com.example.emailservice;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class UserSignupEventConsumer {
    private EmailLogRepository emailLogRepository;

    public UserSignupEventConsumer(EmailLogRepository emailLogRepository) {
        this.emailLogRepository = emailLogRepository;
    }

    @KafkaListener(
            topics = "user.signed-up",
            groupId = "email-service",
            concurrency = "3"
    )
    // 재시도 정책
    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 1000, multiplier = 2),
            dltTopicSuffix = ".dlt"
    )
    public void consume(String message) throws InterruptedException {
        UserSignedUpEvent userSignedUpEvent = UserSignedUpEvent.fromJson(message);

        // (실제 이메일 발송 로직은 생략)하지만 비슷하게 구현하는 척 해보자
        String receiverEmail = userSignedUpEvent.getEmail();
        String subject = userSignedUpEvent.getName() + "님, 회원 가입을 축하드립니다.";
        Thread.sleep(3_000);
        System.out.println("이메일 발송 완료");

        EmailLog emailLog = new EmailLog(
                userSignedUpEvent.getUserId(),
                receiverEmail,
                subject
        );

        emailLogRepository.save(emailLog);
    }
}
