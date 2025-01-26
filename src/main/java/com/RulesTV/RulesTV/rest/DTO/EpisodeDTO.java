package com.RulesTV.RulesTV.rest.DTO;

import java.time.LocalDateTime;

public class EpisodeDTO {
    private String title;
    private String description;
    private int episode_number;
    private int duration_minutes;
    private int season_number;
    private String serie_title;
    private LocalDateTime release_date;
    private LocalDateTime runtime;
    private LocalDateTime runtime_override;
    private String status;

    public EpisodeDTO(String title, String description, int episode_number, int duration_minutes, int season_number,String serie_title, LocalDateTime release_date, LocalDateTime runtime, LocalDateTime runtime_override, String status) {
        this.title = title;
        this.description = description;
        this.episode_number = episode_number;
        this.duration_minutes = duration_minutes;
        this.season_number = season_number;
        this.serie_title = serie_title;
        this.release_date = release_date;
        this.runtime = runtime;
        this.runtime_override = runtime_override;
        this.status = status;
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

    public int getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(int episode_number) {
        this.episode_number = episode_number;
    }

    public int getDuration_minutes() {
        return duration_minutes;
    }

    public void setDuration_minutes(int duration_minutes) {
        this.duration_minutes = duration_minutes;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public String getSerie_title() {
        return serie_title;
    }

    public void setSerie_title(String serie_title) {
        this.serie_title = serie_title;
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

    public LocalDateTime getRuntime_override() {
        return runtime_override;
    }

    public void setRuntime_override(LocalDateTime runtime_override) {
        this.runtime_override = runtime_override;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
