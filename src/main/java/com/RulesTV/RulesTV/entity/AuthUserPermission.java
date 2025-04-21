package com.RulesTV.RulesTV.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "auth_user_permission")
public class AuthUserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private UserAuth user;

    @ManyToOne
    @JoinColumn(name="permission_id",nullable = false)
    private AuthPermission permission;

    public AuthUserPermission(){}

    public AuthUserPermission(UserAuth user,AuthPermission permission){
        this.user = user;
        this.permission = permission;
    }

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

    public AuthPermission getPermission() {
        return permission;
    }

    public void setPermission(AuthPermission permission) {
        this.permission = permission;
    }
}
