package com.example.InstaClone.auth;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String phoneNumber
) {}
