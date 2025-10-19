package com.example.InstaClone.auth;

import com.example.InstaClone.security.JwtUtil;
import com.example.InstaClone.user.User;
import com.example.InstaClone.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse  authenticate(LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );
        User user = (User) auth.getPrincipal();
        String jwt = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(jwt, null);
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.email()).isPresent()) {
            throw new IllegalStateException("Email already taken");
        }

        User user = User.builder()
                .name(registerRequest.firstName())
                .lastName(registerRequest.lastName())
                .email(registerRequest.email())
                .phone(registerRequest.phoneNumber())
                .password(passwordEncoder.encode(registerRequest.password()))
                .build();

        userRepository.save(user);

        String jwt = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(jwt, null);
    }

}
