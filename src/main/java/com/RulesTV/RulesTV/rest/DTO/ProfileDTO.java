package com.RulesTV.RulesTV.rest.DTO;

import jakarta.persistence.Column;
import org.apache.tomcat.util.json.JSONParser;
import java.time.LocalDateTime;

public class ProfileDTO {

    private String name;
    private String email;
    private String user_preferences;
    private int age;
    private LocalDateTime created_at;
    private LocalDateTime update_at;
    private boolean is_kids_profile;
    private String account_email;

    public ProfileDTO(String name, String email,String user_preferences, int age, LocalDateTime created_at, LocalDateTime update_at, boolean is_kids_profile, String account_email) {
        this.name = name;
        this.email = email;
        this.user_preferences = user_preferences;
        this.age = age;
        this.created_at = created_at;
        this.update_at = update_at;
        this.is_kids_profile = is_kids_profile;
        this.account_email = account_email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_preferences() {
        return user_preferences;
    }

    public void setUser_preferences(String user_preferences) {
        this.user_preferences = user_preferences;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
    }

    public boolean isIs_kids_profile() {
        return is_kids_profile;
    }

    public void setIs_kids_profile(boolean is_kids_profile) {
        this.is_kids_profile = is_kids_profile;
    }

    public String getAccount_email() {
        return account_email;
    }

    public void setAccount_email(String account_email) {
        this.account_email = account_email;
    }
}
