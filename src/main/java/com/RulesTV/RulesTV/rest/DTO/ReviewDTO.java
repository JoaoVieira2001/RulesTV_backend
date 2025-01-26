package com.RulesTV.RulesTV.rest.DTO;

import java.time.LocalDateTime;

public class ReviewDTO {
    private int id;
    private String review_text;
    private Integer movie_id;
    private Integer episode_id;
    private Integer profile_id;
    private Integer rating;
    private LocalDateTime created_at;
    private LocalDateTime update_at;

    public ReviewDTO(int id ,String review_text, Integer movie_id, Integer episode_id, Integer profile_id, Integer rating, LocalDateTime created_at, LocalDateTime update_at) {
        this.id = id;
        this.review_text = review_text;
        this.movie_id = movie_id;
        this.episode_id = episode_id;
        this.profile_id = profile_id;
        this.rating = rating;
        this.created_at = created_at;
        this.update_at = update_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    public Integer getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }

    public Integer getEpisode_id() {
        return episode_id;
    }

    public void setEpisode_id(Integer episode_id) {
        this.episode_id = episode_id;
    }

    public Integer getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(Integer profile_id) {
        this.profile_id = profile_id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
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
}
