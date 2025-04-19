// AuthGroupDTO.java
package com.RulesTV.RulesTV.rest.DTO;

import java.util.List;

public class AuthGroupDTO {
    private Integer id;
    private String name;
    private List<UserDTO> users;

    public AuthGroupDTO() {}

    public AuthGroupDTO(Integer id, String name, List<UserDTO> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}

