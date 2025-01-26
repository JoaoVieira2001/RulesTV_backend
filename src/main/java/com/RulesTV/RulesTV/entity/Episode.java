package com.RulesTV.RulesTV.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime releaseDate;

    @Column(nullable = false)
    private int episode_number;

    @Column(nullable = false)
    private int duration_minutes;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime runtime;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime runtime_override;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public enum Status {
        ON_GOING,
        COMPLETED,
        NOT_VIEWED;
    }

    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = false)
    private Serie serie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }





}
