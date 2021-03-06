package ru.example.blog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.example.blog.dto.request.LoginRequest;
import ru.example.blog.dto.request.RegisterRequest;
import ru.example.blog.dto.response.*;

import ru.example.blog.repository.UserRepository;
import ru.example.blog.service.UserService;
import ru.example.blog.service.implementations.CaptchaService;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final CaptchaService captchaService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    
    public ApiAuthController(CaptchaService captchaService, UserService userService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.captchaService = captchaService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication auth = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(getLoginResponse(user.getUsername()));
    }

    private LoginResponse getLoginResponse(String email) {
        ru.example.blog.model.User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        UserLoginResponse userResponse=new UserLoginResponse();
        userResponse.setEmail(currentUser.getEmail());
        userResponse.setName(currentUser.getName());
        userResponse.setModeration(currentUser.getIsModerator() == 1);
        userResponse.setId(currentUser.getId());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
            loginResponse.setUser(userResponse);
        return loginResponse;
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<LoginResponse> logout() {
        SecurityContextHolder.clearContext();
        LoginResponse response = userService.logout();
        return ResponseEntity.ok(response);
    }
}