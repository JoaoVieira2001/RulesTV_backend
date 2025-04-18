package com.RulesTV.RulesTV.services;
import com.RulesTV.RulesTV.entity.AuthPermission;
import com.RulesTV.RulesTV.entity.UserAuth;
import com.RulesTV.RulesTV.repositories.AuthPermissionRepository;
import com.RulesTV.RulesTV.repositories.AuthUserPermissionRepository;
import com.RulesTV.RulesTV.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthUserPermissionService {

    private final AuthUserPermissionRepository permissionRepository;
    private final AuthPermissionRepository authPermissionRepository;
    private final UserRepository userRepository;

    public AuthUserPermissionService(AuthUserPermissionRepository permissionRepository,
                                     AuthPermissionRepository authPermissionRepository,
                                     UserRepository userRepository) {
        this.permissionRepository = permissionRepository;
        this.authPermissionRepository = authPermissionRepository;
        this.userRepository = userRepository;
    }

    public static void checkSuperAdminAccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("You must be authenticated to view this resource.");
        }

        boolean hasAccess = authentication.getAuthorities().stream()
                .anyMatch(auth -> "ROLE_SUPER_ADMIN".equals(auth.getAuthority()));

        if (!hasAccess) {
            throw new AccessDeniedException("You are not authorized to view this resource.");
        }
    }

    public static void checkAdminOrSuperAdminAccess() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("You must be authenticated to view this resource.");
        }

        boolean hasAccess = authentication.getAuthorities().stream()
                .anyMatch(auth ->
                        "ROLE_ADMIN".equals(auth.getAuthority()) ||
                                "ROLE_SUPER_ADMIN".equals(auth.getAuthority())
                );

        if (!hasAccess) {
            throw new AccessDeniedException("You are not authorized to view this resource.");
        }
    }

    public List<AuthPermission> getAllPermissions(){
        checkAdminOrSuperAdminAccess();
        return authPermissionRepository.findAll();
    }

    public AuthPermission getPermissionById(Integer id) {
        checkSuperAdminAccess();
        return authPermissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found with id: " + id));
    }

    public AuthPermission createPermission(AuthPermission permission){
        checkSuperAdminAccess();
        return authPermissionRepository.save(permission);
    }

    public AuthPermission updatePermission(Integer id, AuthPermission updatedPermission){
        checkSuperAdminAccess();
        Optional<AuthPermission> permissionOptional = authPermissionRepository.findById(id);
        if (permissionOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        AuthPermission permission = permissionOptional.get();
        permission.setName(updatedPermission.getName());
        permission.setCodename(updatedPermission.getCodename());

        return authPermissionRepository.save(permission);
    }

    public ResponseEntity<String> deletePermission(Integer id) {
        checkSuperAdminAccess();
        if (!authPermissionRepository.existsById(id)) {
            throw new RuntimeException("Permission not found");
        }
        authPermissionRepository.deleteById(id);
        return null;
    }

    public String assignPermissionToUser(Integer userId, String permissionCodeName){
        Optional<UserAuth> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Optional<AuthPermission> permissionOptional = authPermissionRepository.findByCodename(permissionCodeName);
        if (permissionOptional.isEmpty()) {
            throw new RuntimeException("Permission not found");
        }

        AuthPermission permission = permissionOptional.get();
        UserAuth user = userOptional.get();

        return "Permission assigned successfully";
    }
}
/*
        AuthUserUserPermission userPermission = new AuthUserUserPermission(user, permission);
        authUserUserPermissionRepository.save(userPermission);

        return "Permission assigned successfully";*/