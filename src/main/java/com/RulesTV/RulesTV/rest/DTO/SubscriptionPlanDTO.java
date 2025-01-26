package com.RulesTV.RulesTV.rest.DTO;

import java.time.LocalDateTime;

public class SubscriptionPlanDTO {

    private long price;
    private int max_profiles;
    private String resolution;
    private long amount;
    private String subscription_plan_type;

    public SubscriptionPlanDTO(long price, int max_profiles, String resolution, long amount, String subscription_plan_type) {
        this.price = price;
        this.max_profiles = max_profiles;
        this.resolution = resolution;
        this.amount = amount;
        this.subscription_plan_type = subscription_plan_type;
    }

    public long getPrice() { return price; }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getMax_profiles() {
        return max_profiles;
    }

    public void setMax_profiles(int max_profiles) {
        this.max_profiles = max_profiles;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getSubscription_plan_type() {
        return subscription_plan_type;
    }

    public void setSubscription_plan_type(String subscription_plan_type) {
        this.subscription_plan_type = subscription_plan_type;
    }

}
