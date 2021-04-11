package ru.example.blog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.example.blog.dto.request.LoginRequest;
import ru.example.blog.dto.response.LoginResponse;
import ru.example.blog.dto.response.UserLoginResponse;
import ru.example.blog.repository.UserRepository;

import java.security.Principal;


@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public ApiAuthController(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
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

    @GetMapping("/check")
    public ResponseEntity<LoginResponse> check(Principal principal) {
        if (principal == null) {
            return ResponseEntity.ok(new LoginResponse());
        }
        return ResponseEntity.ok(getLoginResponse(principal.getName()));
    }

    private LoginResponse getLoginResponse(String email) {
        ru.example.blog.model.User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setEmail(currentUser.getEmail());
        userLoginResponse.setName(currentUser.getName());
        userLoginResponse.setModeration(currentUser.getIsModerator() == 1);
        userLoginResponse.setId(currentUser.getId());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUserLoginResponse(userLoginResponse);
        return loginResponse;
    }
}