package com.RulesTV.RulesTV.rest.DTO;

import java.util.List;

public class GroupWithUsersDTO {
    private Integer id;
    private String name;
    private List<UserDTO> users;

    public GroupWithUsersDTO(Integer id, String name, List<UserDTO> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<UserDTO> getUsers() {
        return users;
    }
}
