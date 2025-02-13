package com.RulesTV.RulesTV.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id INT AUTO_INCREMENT PRIMARY KEY
    private int id;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer max_profiles;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Resolution resolution;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionPlanType subscription_plan_type;

    public enum Resolution {
        SD,
        HD,
        TWO_THOUSAND_K;
    }

    public enum SubscriptionPlanType {
        STANDARD,
        PREMIUM;
    }

    @Column(nullable = false)
    private Integer amount;

    @OneToMany(mappedBy = "subscription_plan", cascade = CascadeType.ALL)
    private List<Payment> paymentsList;

    @OneToMany(mappedBy = "subscriptionPlan", cascade = CascadeType.ALL)
    private List<UserAuth> usersList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMax_profiles() {
        return max_profiles;
    }

    public void setMax_profiles(Integer max_profiles) {
        this.max_profiles = max_profiles;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public SubscriptionPlanType getSubscription_plan_type() {
        return subscription_plan_type;
    }

    public void setSubscription_plan_type(SubscriptionPlanType subscription_plan_type) {
        this.subscription_plan_type = subscription_plan_type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<Payment> getPaymentsList() {
        return paymentsList;
    }

    public void setPaymentsList(List<Payment> paymentsList) {
        this.paymentsList = paymentsList;
    }

    public List<UserAuth> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<UserAuth> usersList) {
        this.usersList = this.usersList;
    }
}
