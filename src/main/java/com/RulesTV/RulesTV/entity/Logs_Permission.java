package com.RulesTV.RulesTV.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

public class Logs_Permission implements Serializable {

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

    @ManyToOne
    @JoinColumn(name = "permission_name", referencedColumnName = "name")
    private Permission permission;

    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private UserAuth performedBy;

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

    public String getOldData() {
        return oldData;
    }

    public void setOldData(String oldData) {
        this.oldData = oldData;
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

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public UserAuth getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(UserAuth performedBy) {
        this.performedBy = performedBy;
    }
}
