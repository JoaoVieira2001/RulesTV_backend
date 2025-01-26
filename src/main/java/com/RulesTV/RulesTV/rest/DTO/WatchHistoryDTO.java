package com.RulesTV.RulesTV.rest.DTO;
import org.apache.tomcat.util.json.JSONParser;
import java.time.LocalDateTime;

public class WatchHistoryDTO {

    private LocalDateTime watched_at;
    private int progress;
    private String location;
    private String device_type;
    private String profile_name;
    private String serie_title;
    private String movie_title;

    public WatchHistoryDTO(LocalDateTime watched_at, int progress, String location, String device_type, String profile_name, String serie_title, String movie_title) {
        this.watched_at = watched_at;
        this.progress = progress;
        this.location = location;
        this.device_type = device_type;
        this.profile_name = profile_name;
        this.serie_title = serie_title;
        this.movie_title = movie_title;
    }

    public LocalDateTime getWatched_at() {
        return watched_at;
    }

    public void setWatched_at(LocalDateTime watched_at) {
        this.watched_at = watched_at;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public String getSerie_title() {
        return serie_title;
    }

    public void setSerie_title(String serie_title) {
        this.serie_title = serie_title;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }
}
