package com.RulesTV.RulesTV.rest.DTO;
import java.time.LocalDateTime;

public class SerieDTO {

    private String title;
    private String description;
    private String trailer_url;
    private Integer number_seasons;
    private Integer certifications;
    private LocalDateTime release_date;
    private Byte poster;
    private Integer rating;
    private String status;
    private String language;

    public SerieDTO(String title, String description, String trailer_url, int number_seasons, int certifications, LocalDateTime release_date,
                    Integer rating, String status, String language, Byte poster) {
        this.title = title;
        this.description = description;
        this.trailer_url = trailer_url;
        this.number_seasons = number_seasons;
        this.certifications = certifications;
        this.release_date = release_date;
        this.rating = rating;
        this.status = status;
        this.language = language;
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrailer_url() {
        return trailer_url;
    }

    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }

    public Integer getNumber_seasons() {
        return number_seasons;
    }

    public void setNumber_seasons(Integer number_seasons) {
        this.number_seasons = number_seasons;
    }

    public Integer getCertifications() {
        return certifications;
    }

    public void setCertifications(Integer certifications) {
        this.certifications = certifications;
    }

    public LocalDateTime getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDateTime release_date) {
        this.release_date = release_date;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Byte getPoster() {
        return poster;
    }

    public void setPoster(Byte poster) {
        this.poster = poster;
    }
}
