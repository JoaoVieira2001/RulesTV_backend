package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.AuthPermission;
import com.RulesTV.RulesTV.repositories.AuthPermissionRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthPermissionController {

    private AuthPermissionRepository repository;

    @GetMapping("/permissions/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AuthPermission> getAllPermissions() {
        return repository.findAll();
    }

}
