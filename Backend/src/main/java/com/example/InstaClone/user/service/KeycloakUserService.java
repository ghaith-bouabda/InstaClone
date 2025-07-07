package com.example.InstaClone.user.service;

import com.example.InstaClone.user.dto.UserDto;

import java.util.Optional;

public interface KeycloakUserService {
    void createUser(UserDto dto);
    void updateUser(String userId, UserDto dto);
    void deleteUser(String userId);
    Optional<String> getUserIdByUsername(String username);
}