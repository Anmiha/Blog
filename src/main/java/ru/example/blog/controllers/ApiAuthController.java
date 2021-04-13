package ru.example.blog.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.example.blog.dto.request.AuthenticationRequest;
import ru.example.blog.dto.request.RegisterRequest;
import ru.example.blog.dto.response.CaptchaResponse;
import ru.example.blog.dto.response.LoginResponse;
import ru.example.blog.dto.response.RegisterResponse;
import ru.example.blog.service.UserService;
import ru.example.blog.service.implementations.CaptchaService;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class ApiAuthController {

    private final CaptchaService captchaService;
    private final UserService userService;

    @GetMapping("/check")
    public ResponseEntity<LoginResponse> check(Principal principal) {
        return ResponseEntity.ok(userService.checkUser(principal));
    }

    @GetMapping("/captcha")
    public ResponseEntity<CaptchaResponse> captcha() {
        return captchaService.getCaptchaResponse();
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse response = userService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthenticationRequest requestDto) {
        LoginResponse response = userService.login(requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<LoginResponse> logout() {
        SecurityContextHolder.clearContext();
        LoginResponse response = userService.logout();
        return ResponseEntity.ok(response);
    }
}