package com.RulesTV.RulesTV.entity;

import jakarta.persistence.*;

import java.util.Date;

public class Logs_UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id INT AUTO_INCREMENT PRIMARY KEY
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column(nullable = false, length = 255)
    private String action;

    @Column(columnDefinition = "JSON")
    private String oldData;

    @Column(columnDefinition = "JSON")
    private String newData;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Logs_MovieEpisode.Content_Type content_type;

    public enum Content_Type {
        USER,
        PROFILE
    }

    @Column(nullable = false,unique = true, length = 255)
    private String content_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNewData() {
        return newData;
    }

    public void setNewData(String newData) {
        this.newData = newData;
    }

    public String getOldData() {
        return oldData;
    }

    public void setOldData(String oldData) {
        this.oldData = oldData;
    }

    public Logs_MovieEpisode.Content_Type getContent_type() {
        return content_type;
    }

    public void setContent_type(Logs_MovieEpisode.Content_Type content_type) {
        this.content_type = content_type;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }
}
