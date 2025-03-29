package com.RulesTV.RulesTV.entity;

public class LoginResponse {

    private Number id;
    private String name;
    private String token;
    private long expiresIn;
    private String email;
    private String message;
    private String role;

    public LoginResponse(Number id,String name,String token, long expiresIn, String email, String message,String role) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.expiresIn = expiresIn;
        this.email = email;
        this.message = message;
        this.role = role;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
