package com.Perfulandia.ApiGateway.jwt.controller;

import com.Perfulandia.ApiGateway.jwt.dto.AuthResponse;
import com.Perfulandia.ApiGateway.jwt.dto.LoginRequest;
import com.Perfulandia.ApiGateway.jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}