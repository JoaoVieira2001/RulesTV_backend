package com.RulesTV.RulesTV.entity;

public class LoginResponse {

    private String token;
    private long expiresIn;
    private String message;

    public LoginResponse(String token, long expiresIn, String message) {
        this.token = token;
        this.expiresIn = expiresIn;
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
}
