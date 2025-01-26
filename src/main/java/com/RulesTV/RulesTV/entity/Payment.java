package com.RulesTV.RulesTV.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transiction_id;

    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime payment_date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod payment_method;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus payment_status;

    @Column(columnDefinition = "TEXT")
    private String payment_reason;

    @Column(columnDefinition = "TEXT")
    private String transaction_reference;

    @Column(columnDefinition = "TEXT")
    private String currency;

    public enum PaymentMethod {
        PAYPAL,
        CREDIT_CARD,
        GOOGLE_PLAY,
        MBWAY,
        ATM;
    }

    public enum PaymentStatus  {
        PENDING,
        COMPLETED,
        FAILED;
    }


    @ManyToOne
    @JoinColumn(name = "subscription_plan_id", nullable = false)
    private SubscriptionPlan subscription_plan;

    public int getTransiction_id() {
        return transiction_id;
    }

    public void setTransiction_id(int transiction_id) {
        this.transiction_id = transiction_id;
    }

    public LocalDateTime getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(LocalDateTime payment_date) {
        this.payment_date = payment_date;
    }

    public PaymentMethod getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(PaymentMethod payment_method) {
        this.payment_method = payment_method;
    }

    public PaymentStatus getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(PaymentStatus payment_status) {
        this.payment_status = payment_status;
    }

    public String getPayment_reason() {
        return payment_reason;
    }

    public void setPayment_reason(String payment_reason) {
        this.payment_reason = payment_reason;
    }

    public String getTransaction_reference() {
        return transaction_reference;
    }

    public void setTransaction_reference(String transaction_reference) {
        this.transaction_reference = transaction_reference;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public SubscriptionPlan getSubscription_plan() {
        return subscription_plan;
    }

    public void setSubscription_plan(SubscriptionPlan subscription_plan) {
        this.subscription_plan = subscription_plan;
    }

}
