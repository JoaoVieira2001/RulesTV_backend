package com.RulesTV.RulesTV.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "auth_permission")
public class AuthPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name= "codename",nullable = false)
    private String codename;

    @Column(name= "content_type_id",nullable = false)
    private Integer contentTypeId;

    public AuthPermission(Integer id, String name, String codename, Integer contentTypeId) {
        this.id = id;
        this.name = name;
        this.codename = codename;
        this.contentTypeId = contentTypeId;
    }

    public AuthPermission() {
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

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public Integer getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(Integer contentTypeId) {
        this.contentTypeId = contentTypeId;
    }
}
