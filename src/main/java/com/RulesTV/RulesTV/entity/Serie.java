package com.RulesTV.RulesTV.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Serie")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime releaseDate;

    @Column
    private Integer rating;

    @Column(nullable = false, length = 100)
    private String language;

    @Column(nullable = false)
    private Integer numberSeasons;

    @Column
    private String trailerUrl;

    @Column
    private String poster;

    @Column(nullable = false)
    private Integer certifications;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public enum Status {
        ON_GOING,
        COMPLETED,
        NOT_VIEWED
    }

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<CastPeople> castList;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<Season> seasonList;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<WatchHistory> watchHistoryList;

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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getNumberSeasons() {
        return numberSeasons;
    }

    public void setNumberSeasons(Integer numberSeasons) {
        this.numberSeasons = numberSeasons;
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

    public Integer getCertifications() {
        return certifications;
    }

    public void setCertifications(Integer certifications) {
        this.certifications = certifications;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<CastPeople> getCastList() {
        return castList;
    }

    public void setCastList(List<CastPeople> castList) {
        this.castList = castList;
    }

    public List<Season> getSeasonList() {
        return seasonList;
    }

    public void setSeasonList(List<Season> seasonList) {
        this.seasonList = seasonList;
    }

    public List<WatchHistory> getWatchHistoryList() {
        return watchHistoryList;
    }

    public void setWatchHistoryList(List<WatchHistory> watchHistoryList) {
        this.watchHistoryList = watchHistoryList;
    }

}
