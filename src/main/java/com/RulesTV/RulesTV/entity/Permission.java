package com.RulesTV.RulesTV.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id INT AUTO_INCREMENT PRIMARY KEY
    private int id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL)
    private List<PermissionRole> permissionRoleList;

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

    public List<PermissionRole> getPermissionRoleList() {
        return permissionRoleList;
    }

    public void setPermissionRoleList(List<PermissionRole> permissionRoleList) {
        this.permissionRoleList = permissionRoleList;
    }

}
