package com.RulesTV.RulesTV.entity;

public class LoginResponse {

    private String token;
    private long expiresIn;
    private String email;
    private String message;

    public LoginResponse(String token, long expiresIn, String email, String message) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.email = email;
        this.message = message;
    }

    public LoginResponse(){}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
