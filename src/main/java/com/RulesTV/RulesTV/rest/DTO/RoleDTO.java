package com.RulesTV.RulesTV.rest.DTO;

public class RoleDTO {

    private int id;
    private String name;
    private String typeRole;

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
