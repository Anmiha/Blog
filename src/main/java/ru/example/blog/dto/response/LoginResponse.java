package ru.example.blog.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {
    private boolean result;
    @JsonProperty("user")
    private UserLoginResponse userLoginResponse;

    public LoginResponse() {
    }

    public LoginResponse(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setUserLoginResponse(UserLoginResponse userLoginResponse) {
        this.userLoginResponse = userLoginResponse;
    }
}
