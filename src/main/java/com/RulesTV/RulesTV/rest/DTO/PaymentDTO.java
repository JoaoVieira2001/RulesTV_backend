package com.RulesTV.RulesTV.rest.DTO;

import java.time.LocalDateTime;

public class PaymentDTO {

    private int transaction_id;
    private LocalDateTime payment_date;
    private String payment_method;
    private String payment_status;
    private String payment_reason;
    private String transaction_reference;
    private String subscription_plan_type;
    private String currency;

    public PaymentDTO(int transaction_id, LocalDateTime payment_date, String payment_method, String payment_status, String payment_reason, String transaction_reference, String subscription_plan_type, String currency) {
        this.transaction_id = transaction_id;
        this.payment_date = payment_date;
        this.payment_method = payment_method;
        this.payment_status = payment_status;
        this.payment_reason = payment_reason;
        this.transaction_reference = transaction_reference;
        this.subscription_plan_type = subscription_plan_type;
        this.currency= currency;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public LocalDateTime getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(LocalDateTime payment_date) {
        this.payment_date = payment_date;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
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

    public String getSubscription_plan_type() {
        return subscription_plan_type;
    }

    public void setSubscription_plan_type(String subscription_plan_type) {
        this.subscription_plan_type = subscription_plan_type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
