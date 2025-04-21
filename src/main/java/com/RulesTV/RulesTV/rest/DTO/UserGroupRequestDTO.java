package com.RulesTV.RulesTV.rest.DTO;

import java.util.List;

public class UserGroupRequestDTO {
    private Integer userId;
    private String groupName;
    private List<Integer> userIds;

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

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }
}
