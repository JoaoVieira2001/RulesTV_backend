package com.RulesTV.RulesTV.rest.DTO;

import java.time.LocalDateTime;

public class LibraryDTO {
    private int id;
    private LocalDateTime added_at;
    private String category;
    private int content_id;
    private String content_type;
    private String profile_name;

    public LibraryDTO(int id, LocalDateTime added_at, String category, int content_id, String content_type, String profile_name) {
        this.id = id;
        this.added_at = added_at;
        this.category = category;
        this.content_id = content_id;
        this.content_type = content_type;
        this.profile_name = profile_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getAdded_at() {
        return added_at;
    }

    public void setAdded_at(LocalDateTime added_at) {
        this.added_at = added_at;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }
}
