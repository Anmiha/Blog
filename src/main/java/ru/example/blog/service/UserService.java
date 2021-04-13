package ru.example.blog.service;

import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import ru.example.blog.dto.request.AuthenticationRequest;
import ru.example.blog.dto.request.PasswordChangeRequest;
import ru.example.blog.dto.request.ProfileRequest;
import ru.example.blog.dto.request.RegisterRequest;
import ru.example.blog.dto.response.LoginResponse;
import ru.example.blog.dto.response.RegisterResponse;
import ru.example.blog.dto.response.base.ResultResponse;
import ru.example.blog.dto.response.type.PasswordChangeResponse;
import ru.example.blog.model.User;
import ru.example.blog.model.enums.Permission;


import java.security.Principal;
import java.util.Map;

public interface UserService {
    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(AuthenticationRequest authenticationRequest);

    LoginResponse logout();

    LoginResponse checkUser(Principal principal);

    ResultResponse<?> restorePassword(String email);

    ResultResponse<PasswordChangeResponse> changePassword(PasswordChangeRequest request);

    User getCurrentUser();

    boolean getUserPermission(Permission permission);

    ResultResponse<Map<String,String>> editProfile(ProfileRequest request, Errors errors);

    ResultResponse<Map<String, String>> updateProfileWithPhoto(MultipartFile photo,
                                                          Boolean removePhoto,
                                                          String name,
                                                          String email,
                                                          String password);
}
