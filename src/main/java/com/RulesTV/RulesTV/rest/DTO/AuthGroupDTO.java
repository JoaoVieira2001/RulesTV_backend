package com.RulesTV.RulesTV.rest.DTO;

public class AuthGroupDTO {
    private String groupName;

    public AuthGroupDTO(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
