package com.example.userservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserService(UserRepository userRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void signUp(SignUpRequestDto signUpRequestDto) {
        User user = new User(
                signUpRequestDto.email(),
                signUpRequestDto.name(),
                signUpRequestDto.password()
        );
        User savedUser =userRepository.save(user);

        UserSignedUpEvent userSignedUpEvent = new UserSignedUpEvent(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName()
        );
        this.kafkaTemplate.send("user.signed-up", toJsonString(userSignedUpEvent));
    }

    private String toJsonString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String message = mapper.writeValueAsString(object);
            return message;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json 직렬화 실패");
        }
    }
}
