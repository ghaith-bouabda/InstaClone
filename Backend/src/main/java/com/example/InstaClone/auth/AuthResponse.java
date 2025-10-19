package com.example.InstaClone.auth;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
