package com.RulesTV.RulesTV.services;
import com.RulesTV.RulesTV.entity.AuthPermission;
import com.RulesTV.RulesTV.entity.UserAuth;
import com.RulesTV.RulesTV.repositories.AuthPermissionRepository;
import com.RulesTV.RulesTV.repositories.AuthUserPermissionRepository;
import com.RulesTV.RulesTV.repositories.UserRepository;

import java.util.Optional;

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