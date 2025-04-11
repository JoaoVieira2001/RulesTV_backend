package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.AuthPermission;
import com.RulesTV.RulesTV.repositories.AuthPermissionRepository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class AuthPermissionController {

    private AuthPermissionRepository repository;

    @GetMapping
    public List<AuthPermission> getAllPermissions() {
        return repository.findAll();
    }

}
