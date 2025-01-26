package com.RulesTV.RulesTV.rest.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class AccountDTO {

    private String email;
    private String name;
    private String password;
    private String profilePicture;
    private LocalDateTime signupDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private RoleDTO role;
    private String subscription_type;

    public AccountDTO(String email, String name, String password, String profilePicture,
                      LocalDateTime signupDate, LocalDateTime createdAt, LocalDateTime updatedAt,
                      RoleDTO role, String subscription_type) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.profilePicture = profilePicture;
        this.signupDate = signupDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
        this.subscription_type = subscription_type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public LocalDateTime getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(LocalDateTime signupDate) {
        this.signupDate = signupDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public String getSubscription_type() {
        return subscription_type;
    }

    public void setSubscription_type(String subscription_type) {
        this.subscription_type = subscription_type;
    }

}
