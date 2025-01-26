package com.RulesTV.RulesTV.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime releaseDate;

    @Column
    private int durationMinutes;

    @Column(nullable = false, length = 255)
    private String audio;

    @Column(length = 255)
    private String subtitle;

    @Column(nullable = false)
    private double rating;

    @Column(length = 255)
    private String poster;

    @Column(length = 255)
    private String trailerUrl;

    @Column(nullable = false)
    private int certifications;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime runtime;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<CastPeople> castList;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieGenre> movieGenreList;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieTag> movieTagList;

    @OneToOne
    @JoinColumn(name = "watch_history_id", referencedColumnName = "id", unique = true)
    private WatchHistory watchHistory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
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

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public int getCertifications() {
        return certifications;
    }

    public void setCertifications(int certifications) {
        this.certifications = certifications;
    }

    public LocalDateTime getRuntime() {
        return runtime;
    }

    public void setRuntime(LocalDateTime runtime) {
        this.runtime = runtime;
    }

    public List<CastPeople> getCastList() {
        return castList;
    }

    public void setCastList(List<CastPeople> castList) {
        this.castList = castList;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }


    public List<MovieGenre> getMovieGenreList() {
        return movieGenreList;
    }

    public void setMovieGenreList(List<MovieGenre> movieGenreList) {
        this.movieGenreList = movieGenreList;
    }

    public List<MovieTag> getMovieTagList() {
        return movieTagList;
    }

    public void setMovieTagList(List<MovieTag> movieTagList) {
        this.movieTagList = movieTagList;
    }

    public WatchHistory getWatchHistory() {
        return watchHistory;
    }

    public void setWatchHistory(WatchHistory watchHistory) {
        this.watchHistory = watchHistory;
    }
}
