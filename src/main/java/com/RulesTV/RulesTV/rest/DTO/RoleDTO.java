package com.RulesTV.RulesTV.rest.DTO;

import com.RulesTV.RulesTV.entity.Account;

import java.util.List;

public class RoleDTO {

    private int id;
    private String name;
    private String typeRole;
    private List<AccountDTO> accountsList;

    public RoleDTO(int id, String name, String typeRole) {
        this.id = id;
        this.name = name;
        this.typeRole = typeRole;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeRole() {
        return typeRole;
    }

    public void setTypeRole(String typeRole) {
        this.typeRole = typeRole;
    }

}
