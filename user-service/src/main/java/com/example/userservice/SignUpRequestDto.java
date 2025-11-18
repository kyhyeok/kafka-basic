package com.example.userservice;

public record SignUpRequestDto(
    String email,
    String name,
    String password
) {
}
