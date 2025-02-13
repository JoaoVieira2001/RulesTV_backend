package com.RulesTV.RulesTV.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id INT AUTO_INCREMENT PRIMARY KEY
    private int id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Notification_Type type;

    public enum Notification_Type {
        EMAIL,
        PUSH,
        IN_APP;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Notification_Priority priority;

    public enum Notification_Priority {
        LOW,
        MEDIUM,
        HIGH;
    }

    @Column(nullable = false)
    private boolean status = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime readAt;

    private String actionUrl;

    @ManyToOne
    @JoinColumn(name = "performed_by", referencedColumnName = "email", nullable = false)
    private UserAuth performedBy;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Payment transaction;

    @ManyToOne
    @JoinColumn(name = "movie_title", referencedColumnName = "title")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "episode_title", referencedColumnName = "title")
    private Episode episode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Notification_Type getType() {
        return type;
    }

    public void setType(Notification_Type type) {
        this.type = type;
    }

    public Notification_Priority getPriority() {
        return priority;
    }

    public void setPriority(Notification_Priority priority) {
        this.priority = priority;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public UserAuth getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(UserAuth performedBy) {
        this.performedBy = performedBy;
    }

    public Payment getTransaction() {
        return transaction;
    }

    public void setTransaction(Payment transaction) {
        this.transaction = transaction;
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
}
