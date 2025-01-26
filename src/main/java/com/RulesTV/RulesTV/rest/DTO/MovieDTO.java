package com.RulesTV.RulesTV.rest.DTO;
import java.time.LocalDateTime;

public class MovieDTO {

    private String title;
    private String description;
    private LocalDateTime release_date;
    private LocalDateTime runtime;
    private int duration_minutes;
    private String audio;
    private String subtitle;
    private double rating;
    private String poster;
    private String trailer_url;
    private int certifications;
    private WatchHistoryDTO watchHistory;

    public MovieDTO(String title, String description, LocalDateTime release_date, LocalDateTime runtime, int duration_minutes, String audio, String subtitle, double rating, String poster, String trailer_url, int certifications, WatchHistoryDTO watchHistory) {
        this.title = title;
        this.description = description;
        this.release_date = release_date;
        this.runtime = runtime;
        this.duration_minutes = duration_minutes;
        this.audio = audio;
        this.subtitle = subtitle;
        this.rating = rating;
        this.poster = poster;
        this.trailer_url = trailer_url;
        this.certifications = certifications;
        this.watchHistory = watchHistory;
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

    public LocalDateTime getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDateTime release_date) {
        this.release_date = release_date;
    }

    public LocalDateTime getRuntime() {
        return runtime;
    }

    public void setRuntime(LocalDateTime runtime) {
        this.runtime = runtime;
    }

    public int getDuration_minutes() {
        return duration_minutes;
    }

    public void setDuration_minutes(int duration_minutes) {
        this.duration_minutes = duration_minutes;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTrailer_url() {
        return trailer_url;
    }

    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }

    public int getCertifications() {
        return certifications;
    }

    public void setCertifications(int certifications) {
        this.certifications = certifications;
    }

    public WatchHistoryDTO getWatchHistory() {
        return watchHistory;
    }

    public void setWatchHistory(WatchHistoryDTO watchHistory) {
        this.watchHistory = watchHistory;
    }
}
