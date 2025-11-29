package com.cloudferm.controller;

import com.cloudferm.dto.UserResponse;
import com.cloudferm.model.Role;
import com.cloudferm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final com.cloudferm.service.AuthService authService;

    @GetMapping("/operators")
    public ResponseEntity<List<UserResponse>> getOperators() {
        List<UserResponse> operators = userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.OPERATOR)
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(operators);
    }

    @org.springframework.web.bind.annotation.PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createUser(@org.springframework.web.bind.annotation.RequestBody com.cloudferm.dto.RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User created successfully");
    }
}
