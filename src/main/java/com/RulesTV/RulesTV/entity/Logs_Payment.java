package com.RulesTV.RulesTV.entity;

import jakarta.persistence.*;

import java.util.Date;

public class Logs_Payment {

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

    @Column(nullable = false, length = 255)
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private UserAuth performedBy;

    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id")
    private Payment transaction_id;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserAuth getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(UserAuth performedBy) {
        this.performedBy = performedBy;
    }

    public Payment getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Payment transaction_id) {
        this.transaction_id = transaction_id;
    }
}
