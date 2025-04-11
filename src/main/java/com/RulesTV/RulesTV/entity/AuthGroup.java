package com.RulesTV.RulesTV.entity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "auth_group")
public class AuthGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "group")
    private Set<AuthUserGroup> userGroups;

    public AuthGroup(Integer id, String name, Set<AuthUserGroup> userGroups) {
        this.id = id;
        this.name = name;
        this.userGroups = userGroups;
    }

    public AuthGroup() {
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

    public Set<AuthUserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<AuthUserGroup> userGroups) {
        this.userGroups = userGroups;
    }
}
