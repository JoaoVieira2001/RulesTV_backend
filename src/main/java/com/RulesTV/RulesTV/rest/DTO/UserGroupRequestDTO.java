package com.RulesTV.RulesTV.rest.DTO;

public class UserGroupRequestDTO {
    private Integer userId;
    private String groupName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
