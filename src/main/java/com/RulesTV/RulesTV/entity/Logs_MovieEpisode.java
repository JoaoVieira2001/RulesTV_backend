package com.RulesTV.RulesTV.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Logs_MovieEpisode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id INT AUTO_INCREMENT PRIMARY KEY
    private int id;

    @Column(nullable = false)
    private String contentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Content_Type content_type;

    public enum Content_Type {
        SERIE,
        MOVIE,
        EPISODE;
    }

    @Column(nullable = false, length = 255)
    private String action;

    @Column(columnDefinition = "JSON")
    private String oldData;

    @Column(columnDefinition = "JSON")
    private String newData;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "movie_title", referencedColumnName = "title")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "episode_title", referencedColumnName = "title")
    private Episode episode;

    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private UserAuth user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Content_Type getContent_type() {
        return content_type;
    }

    public void setContent_type(Content_Type content_type) {
        this.content_type = content_type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getOldData() {
        return oldData;
    }

    public void setOldData(String oldData) {
        this.oldData = oldData;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getNewData() {
        return newData;
    }

    public void setNewData(String newData) {
        this.newData = newData;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public UserAuth getUser() {
        return user;
    }

    public void setUser(UserAuth user) {
        this.user = user;
    }
}
