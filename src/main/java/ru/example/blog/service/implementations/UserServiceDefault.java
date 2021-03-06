package ru.example.blog.service.implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import ru.example.blog.config.AppConfig;
import ru.example.blog.dto.request.AuthenticationRequest;
import ru.example.blog.dto.request.PasswordChangeRequest;
import ru.example.blog.dto.request.ProfileRequest;
import ru.example.blog.dto.request.RegisterRequest;
import ru.example.blog.dto.response.LoginResponse;
import ru.example.blog.dto.response.RegisterResponse;
import ru.example.blog.dto.response.UserLoginResponse;
import ru.example.blog.dto.response.UserResponse;
import ru.example.blog.dto.response.base.ResultResponse;
import ru.example.blog.dto.response.type.PasswordChangeResponse;
import ru.example.blog.dto.response.type.RegisterErrorResponse;
import ru.example.blog.enums.ModerationStatus;
import ru.example.blog.exception.ApiError;
import ru.example.blog.exception.BadRequestException;
import ru.example.blog.model.CaptchaCode;
import ru.example.blog.model.User;
import ru.example.blog.repository.CaptchaCodeRepository;
import ru.example.blog.repository.PostRepository;
import ru.example.blog.repository.UserRepository;
import ru.example.blog.security.SecurityUser;
import ru.example.blog.service.MailSender;
import ru.example.blog.service.StorageService;
import ru.example.blog.service.UserService;

import ru.example.blog.model.enums.Permission;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceDefault implements UserService {

    private final AppConfig appConfig;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CaptchaCodeRepository captchaCodeRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private final MailSender mailSender;
    private final StorageService storageService;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        RegisterResponse response = checkErrors(registerRequest);

        if (response.isResult()) {
            User newUser = registerNewUser(registerRequest);
            log.info("IN register - user: {} successfully register", newUser.getName());
        }

        return response;
    }

    private User registerNewUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setIsModerator(0);
        user.setName(registerRequest.getName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRegTime(LocalDateTime.now());

        return userRepository.save(user);
    }

    private RegisterResponse checkErrors(RegisterRequest registerRequest) {
        RegisterResponse response = new RegisterResponse();
        RegisterErrorResponse errors = new RegisterErrorResponse();

        if (checkEmail(registerRequest.getEmail())) {
            errors.setEmail("Этот e-mail уже зарегистрирован");
            response.setResult(false);
        }
        if (registerRequest.getName().isBlank()) {
            errors.setName("Имя указано неверно");
            response.setResult(false);
        }
        if (registerRequest.getPassword().length() < 6) {
            errors.setPassword("Пароль короче 6-ти символов");
            response.setResult(false);
        }
        if (!checkCaptcha(registerRequest.getCaptcha(), registerRequest.getCaptchaSecret())) {
            errors.setCaptcha("Код с картинки введен неверно");
            response.setResult(false);
        }
        response.setErrors(errors);
        return response;
    }

    private boolean checkCaptcha(String captcha, String captchaCodeSecret) {
        String code = captchaCodeRepository.findBySecretCode(captchaCodeSecret)
                .map(CaptchaCode::getCode).orElse("");

        return captcha.equals(code);
    }

    private boolean checkEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public LoginResponse login(AuthenticationRequest authenticationRequest) {
        LoginResponse response = new LoginResponse();
        String username = authenticationRequest.getEmail();

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            authenticationRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            SecurityUser securityUser = (SecurityUser) auth.getPrincipal();

        } catch (Exception ex) {
            return response;
        }

        Optional<User> optionalUser = userRepository.findByEmail(username);

        User currentUser;
        if (optionalUser.isPresent()) {
            currentUser = optionalUser.get();
        } else {
            return response;
        }

        boolean isModerator = currentUser.getIsModerator() == 1;

        response.setResult(true);
        response.setUserResponse(UserResponse.builder()
                .id(currentUser.getId())
                .name(currentUser.getName())
                .photo(currentUser.getPhoto())
                .email(currentUser.getEmail())
                .moderation(isModerator)
                .moderationCount(moderationPostCount(isModerator))
                .settings(isModerator)
                .build()
        );

        log.info("Login user.email - {}", authenticationRequest.getEmail());

        return response;
    }

    @Override
    public LoginResponse checkUser(Principal principal) {
        LoginResponse response = new LoginResponse();
        User user;

        if (principal == null) {
            return response;
        }

        Optional<User> optionalUser = userRepository.findByEmail(principal.getName());

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            return response;
        }

        boolean isModerator = user.getIsModerator() == 1;

        response.setResult(true);
        response.setUserResponse(UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .photo(user.getPhoto())
                .email(user.getEmail())
                .moderation(isModerator)
                .moderationCount(moderationPostCount(isModerator))
                .settings(isModerator)
                .build()
        );

        return response;
    }

    private int moderationPostCount(boolean isModerator) {
        if (!isModerator) return 0;

        return (int) postRepository
                .findPostForModeration(ModerationStatus.NEW, PageRequest.of(0, 10))
                .getTotalElements();
    }

    @Override
    public ResultResponse<?> restorePassword(String email) {
        ResultResponse<String> result = new ResultResponse<>();
        User user;

        //check User exist
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            result.setResult(false);
            return result;
        } else {
            user = optionalUser.get();
        }

        //sendEmail
        String activationCode = UUID.randomUUID().toString();
        String message = generateRestoreMessage(user.getName(), activationCode);

        if (!email.isBlank()) {
            user.setCode(activationCode);
            userRepository.save(user);
            mailSender.send(email, "[Test diploma] Restore password", message);
        }
        return new ResultResponse<>();
    }

    @Override
    public ResultResponse<PasswordChangeResponse> changePassword(PasswordChangeRequest request) {
        ResultResponse<PasswordChangeResponse> response = new ResultResponse<>();
        PasswordChangeResponse errors = new PasswordChangeResponse();

        if (request.getPassword().length() < 6) {
            errors.addError("password", "Пароль короче 6-ти символов");
            response.setResult(false);
            response.setErrors(errors);
            return response;
        }
        Optional<User> user = userRepository.findByCode(request.getCode());
        if (user.isEmpty()) {
            errors.addError("code", "Ссылка для восстановления пароля устарела.\n" +
                    "<a href=\n" +
                    "\\\"/auth/restore\\\">Запросить ссылку снова</a>");
        }
        CaptchaCode code = captchaCodeRepository.findBySecretCode(request.getCaptchaSecret())
                .orElse(new CaptchaCode());
        if (!code.getCode().equals(request.getCaptcha())) {
            errors.addError("captcha", "Код с картинки введен неверно");
        }

        if (errors.getErrors().size() != 0) {
            response.setResult(false);
            response.setErrors(errors);
            return response;
        }

        //if all is OK
        user.ifPresent(u -> {
            u.setPassword(passwordEncoder.encode(request.getPassword()));
            u.setCode(null);
            userRepository.save(u);
        });

        return new ResultResponse<>();
    }

    @Override
    public ResultResponse<Map<String, String>> editProfile(ProfileRequest request, Errors formErrors) {
        ResultResponse<Map<String, String>> response = new ResultResponse<>();
        boolean isChangedEmail = false;

        String currentEmail = ((SecurityUser) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal())
                .getUsername();
        User currUser = userRepository.findByEmail(currentEmail).orElseThrow(
                () -> new BadRequestException(new ApiError())
        );

        Map<String, String> errors = new HashMap<>();
        if (formErrors.hasErrors()) {
            errors.putAll(takeFormErrors(formErrors));
            response.setErrors(errors);
            response.setResult(false);
            return response;
        }

        errors.putAll(checkEditProfileRequest(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        ));
        errors.putAll(checkEmailDuplicate(currentEmail, request.getEmail()));

        if (errors.size() != 0) {
            response.setResult(false);
            response.setErrors(errors);
            return response;
        }

        Optional.ofNullable(request.getName()).ifPresent(currUser::setName);

        if (!currentEmail.equals(currUser.getEmail())) {
            isChangedEmail = true;
        }
        if (!request.getEmail().isBlank()) {
            currUser.setEmail(request.getEmail());
        }

        Optional.ofNullable(request.getPassword())
                .ifPresent(p -> currUser.setPassword(passwordEncoder.encode(p)));

        if (request.getRemovePhoto() == 1) {
            currUser.setPhoto("");
        }

        userRepository.save(currUser);

        if (isChangedEmail) {
            SecurityContextHolder.clearContext();
        }

        return response;
    }

    private Map<String, String> takeFormErrors(Errors errors) {
        Map<String, String> profileErrors = new HashMap<>();

        errors.getFieldErrors()
                .forEach(error -> profileErrors.put(error.getField(), error.getDefaultMessage()));

        return profileErrors;
    }

    @Override
    public ResultResponse<Map<String, String>> updateProfileWithPhoto(
            MultipartFile photo,
            Boolean removePhoto,
            String name,
            String email,
            String password
    ) {
        ResultResponse<Map<String, String>> response = new ResultResponse<>();
        boolean isChangedEmail = false;

        String currentEmail = ((SecurityUser) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal())
                .getUsername();
        User currUser = userRepository.findByEmail(currentEmail).orElseThrow(
                () -> new BadRequestException(new ApiError()));

        Map<String, String> errors = checkEditProfileRequest(name, email, password);
        errors.putAll(checkEditProfilePhoto(photo));

        if (errors.size() != 0) {
            response.setResult(false);
            response.setErrors(errors);
            return response;
        }

        String pathToPhoto = "/" + storageService.handleFileUploadAvatar(photo);
        currUser.setPhoto(pathToPhoto);

        Optional.ofNullable(name).ifPresent(currUser::setName);
        Optional.ofNullable(password).ifPresent(p -> currUser.setPassword(passwordEncoder.encode(p)));

        if (!currentEmail.equals(currUser.getEmail())) {
            isChangedEmail = true;
        }
        if (!email.isBlank()) {
            currUser.setEmail(email);
        }

        userRepository.save(currUser);

        if (isChangedEmail) {
            SecurityContextHolder.clearContext();
        }

        return response;
    }

    private Map<String, String> checkEmailDuplicate(String currentEmail, String targetEmail) {
        Map<String, String> result = new HashMap<>();
        userRepository.findByEmail(targetEmail)
                .ifPresent(u -> {
                    if (!u.getEmail().equals(currentEmail))
                        result.put("email", "Этот e-mail уже зарегистрирован");
                });
        return result;
    }

    private Map<String, String> checkEditProfilePhoto(MultipartFile file) {
        Map<String, String> result = new HashMap<>(Collections.emptyMap());
        Optional.ofNullable(file).map(f -> {
            if (f.getSize() > 5 * 1024 * 1024) {
                result.put("photo", "Фото слишком большое, нужно не более 5 Мб");
            }
            return false;
        });
        return result;
    }

    private Map<String, String> checkEditProfileRequest(String name, String email, String password) {
        Map<String, String> result = new HashMap<>(Collections.emptyMap());

        if (email.isBlank()) {
            result.put("email", "Не указан e-mail");
        }

        if (password != null) {
            if (password.length() < 6) {
                result.put("password", "Пароль короче 6-ти символов");
            }
        }

        if (name.isBlank()) {
            result.put("name", "Имя указано неверно");
        }

        return result;
    }

    private String generateRestoreMessage(String userName, String activationCode) {
        return String.format(
                "Привет, %s! \n Добро пожаловать на сайт DevPub. " +
                        "Для подтверждения перейди по ссылке: %s/login/change-password/%s",
                userName, appConfig.getHostname(), activationCode
        );
    }

    @Override
    public LoginResponse logout() {
        LoginResponse response = new LoginResponse();
        response.setResult(true);
        return response;
    }

    @Override
    public User getCurrentUser() {
        String email = ((SecurityUser) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("user not found")
        );
    }

    @Override
    public boolean getUserPermission(Permission permission) {
        return SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(permission.getPermission()));
    }

}
