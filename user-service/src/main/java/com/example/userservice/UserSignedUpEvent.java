package com.example.userservice;

public record UserSignedUpEvent(
        Long userId,
        String email,
        String name
) {
}
