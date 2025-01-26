package com.RulesTV.RulesTV.entity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id INT AUTO_INCREMENT PRIMARY KEY
    private int id;

    @Column(nullable = false, unique = true, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_role", nullable = false)
    private TypeRole typeRole;

    @OneToMany(mappedBy = "role")
    private Set<Account> accountsList;

    @OneToMany(mappedBy = "role")
    private Set<PermissionRole> permissionsRoleList;

    public enum TypeRole {
        PARENTAL,
        CHILD;
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

    public TypeRole getTypeRole() {
        return typeRole;
    }

    public void setTypeRole(TypeRole typeRole) {
        this.typeRole = typeRole;
    }

    public Set<Account> getAccountsList() {
        return accountsList;
    }

    public void setAccountsList(Set<Account> accountsList) {
        this.accountsList = accountsList;
    }

    public Set<PermissionRole> getPermissionsRoleList() {
        return permissionsRoleList;
    }

    public void setPermissionsRoleList(Set<PermissionRole> permissionsRoleList) {
        this.permissionsRoleList = permissionsRoleList;
    }
}
