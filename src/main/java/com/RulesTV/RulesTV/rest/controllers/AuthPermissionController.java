package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.AuthPermission;
import com.RulesTV.RulesTV.repositories.AuthPermissionRepository;
import com.RulesTV.RulesTV.services.AuthUserPermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/permissions")
public class AuthPermissionController {

    private AuthPermissionRepository repository;
    private final AuthUserPermissionService permissionService;

    public AuthPermissionController(AuthUserPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/all")
    public List<AuthPermission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthPermission> getPermissionById(@PathVariable Integer id) {
        AuthPermission permission = permissionService.getPermissionById(id);
        return ResponseEntity.ok(permission);
    }

    @PostMapping("/post")
    public AuthPermission createPermission(@RequestBody AuthPermission permission) {
        return permissionService.createPermission(permission);
    }

    @PutMapping("/put/{id}")
    public AuthPermission updatePermission(
            @PathVariable Integer id,
            @RequestBody AuthPermission updatedPermission) {
        return permissionService.updatePermission(id, updatedPermission);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePermission(@PathVariable Integer id) {
        return permissionService.deletePermission(id);
    }


}
