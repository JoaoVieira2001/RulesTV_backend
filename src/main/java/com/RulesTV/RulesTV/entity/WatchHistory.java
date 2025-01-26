package com.RulesTV.RulesTV.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class WatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime watchedAt;

    @Column
    private Integer progress;

    @Column(length = 255)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", nullable = false)
    private DeviceType deviceType;

    @ManyToOne
    @JoinColumn(name = "serie_id", referencedColumnName = "id",nullable = true)
    private Serie serie;

    @OneToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id", unique = true, nullable = true)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id",nullable = true)
    private Profile profile;

    public enum DeviceType {
        MOBILE,
        TV;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getWatchedAt() {
        return watchedAt;
    }

    public void setWatchedAt(LocalDateTime watchedAt) {
        this.watchedAt = watchedAt;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

}
