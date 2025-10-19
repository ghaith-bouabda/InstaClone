package com.example.InstaClone.auth;

public record LoginRequest(
        String email,
        String password
) {}
