package com.example.InstaClone.user.service.impl;

import com.example.InstaClone.user.dto.UserDto;
import com.example.InstaClone.user.service.KeycloakUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class KeycloakUserServiceImpl implements KeycloakUserService {
    @Override
    public void createUser(UserDto dto) {

    }

    @Override
    public void updateUser(String userId, UserDto dto) {

    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public Optional<String> getUserIdByUsername(String username) {
        return Optional.empty();
    }
}
