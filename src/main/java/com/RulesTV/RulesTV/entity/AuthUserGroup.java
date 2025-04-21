package com.RulesTV.RulesTV.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "auth_user_groups",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "group_id"})}
)
public class AuthUserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserAuth user;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    private AuthGroup group;

    public AuthUserGroup(Integer id, UserAuth user, AuthGroup group) {
        this.id = id;
        this.user = user;
        this.group = group;
    }

    public AuthUserGroup(){}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserAuth getUser() {
        return user;
    }

    public void setUser(UserAuth user) {
        this.user = user;
    }

    public AuthGroup getGroup() {
        return group;
    }

    public void setGroup(AuthGroup group) {
        this.group = group;
    }

}

