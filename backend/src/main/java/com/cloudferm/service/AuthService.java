package com.cloudferm.service;

import com.cloudferm.dto.AuthResponse;
import com.cloudferm.dto.LoginRequest;
import com.cloudferm.dto.RegisterRequest;
import com.cloudferm.model.User;
import com.cloudferm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .active(true)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .role(user.getRole().name())
                .fullName(user.getFullName())
                .build();
    }

    public AuthResponse authenticate(LoginRequest request) {
        System.out.println("Attempting login for: " + request.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            System.out.println("Authentication successful for: " + request.getEmail());
        } catch (Exception e) {
            System.out.println("Authentication failed for: " + request.getEmail() + " - " + e.getMessage());
            throw e;
        }
        
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .role(user.getRole().name())
                .fullName(user.getFullName())
                .build();
    }
}
