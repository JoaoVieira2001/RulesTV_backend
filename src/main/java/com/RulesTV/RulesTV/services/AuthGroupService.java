package com.RulesTV.RulesTV.services;

import com.RulesTV.RulesTV.entity.AuthGroup;
import com.RulesTV.RulesTV.entity.AuthPermission;
import com.RulesTV.RulesTV.entity.AuthUserGroup;
import com.RulesTV.RulesTV.entity.UserAuth;
import com.RulesTV.RulesTV.repositories.AuthGroupRepository;
import com.RulesTV.RulesTV.repositories.AuthPermissionRepository;
import com.RulesTV.RulesTV.repositories.AuthUserGroupRepository;
import com.RulesTV.RulesTV.repositories.UserRepository;
import com.RulesTV.RulesTV.rest.DTO.AuthGroupDTO;
import com.RulesTV.RulesTV.rest.DTO.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthGroupService {

    private final AuthGroupRepository authGroupRepository;
    private final AuthUserGroupRepository authUserGroupRepository;
    private final UserRepository userRepository;
    private final AuthPermissionRepository authPermissionRepository;

    public AuthGroupService(AuthGroupRepository authGroupRepository,
                            AuthUserGroupRepository authUserGroupRepository,
                            UserRepository userRepository, AuthPermissionRepository authPermissionRepository) {
        this.authGroupRepository = authGroupRepository;
        this.authUserGroupRepository = authUserGroupRepository;
        this.userRepository = userRepository;
        this.authPermissionRepository = authPermissionRepository;
    }

    // Get all groups with the associated users
    public List<AuthGroupDTO> getAllGroups() {
        AuthUserPermissionService.checkAdminOrSuperAdminAccess();

        List<AuthGroup> groups = authGroupRepository.findAllWithUsers();

        return groups.stream().map(group -> {
            List<UserDTO> users = group.getUserGroups().stream()
                    .map(ug -> {
                        UserAuth user = ug.getUser();
                        return new UserDTO(user.getId(), user.getUsername());
                    })
                    .toList();

            return new AuthGroupDTO(group.getId(), group.getName(), users);
        }).toList();
    }

    // Get all users of a specific group
    public List<UserAuth> getUsersInGroup(String groupName){
        AuthUserPermissionService.checkAdminOrSuperAdminAccess();
        Optional<AuthGroup> groupOptional = authGroupRepository.findByName(groupName);
        if (groupOptional.isEmpty()) {
            throw new RuntimeException("Group not found");
        }

        AuthGroup group = groupOptional.get();
        return authUserGroupRepository.findByGroup(group).stream()
                .map(AuthUserGroup::getUser)
                .toList();
    }

    //Get a group by its id
    public AuthGroupDTO getGroupById(Integer groupId) {
        AuthUserPermissionService.checkAdminOrSuperAdminAccess();

        AuthGroup group = authGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        List<UserDTO> users = group.getUserGroups().stream()
                .map(ug -> {
                    UserAuth user = ug.getUser();
                    return new UserDTO(user.getId(), user.getUsername());
                })
                .toList();

        return new AuthGroupDTO(group.getId(), group.getName(), users);
    }

    public AuthGroup createGroup(String groupName) {
        AuthUserPermissionService.checkSuperAdminAccess();
        if (authGroupRepository.findByName(groupName).isPresent()) {
            throw new IllegalArgumentException("Group with this name already exists");
        }
        AuthGroup group = new AuthGroup();
        group.setName(groupName);
        return authGroupRepository.save(group);
    }

    public AuthGroup addUserToGroup(Integer userId, String groupName, Integer permissionId) {
        AuthUserPermissionService.checkAdminOrSuperAdminAccess();

        Optional<UserAuth> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Optional<AuthGroup> groupOptional = authGroupRepository.findByName(groupName);
        if (groupOptional.isEmpty()) {
            throw new RuntimeException("Group not found");
        }

        Optional<AuthPermission> permissionOptional = authPermissionRepository.findById(permissionId);
        if (permissionOptional.isEmpty()) {
            throw new RuntimeException("Permission not found");
        }

        AuthGroup group = groupOptional.get();
        UserAuth user = userOptional.get();
        AuthPermission permission = permissionOptional.get();

        if (group.getUserGroups().size() >= 6) {
            throw new RuntimeException("Group has reached its maximum user limit (6)");
        }

        boolean alreadyInGroup = group.getUserGroups().stream()
                .anyMatch(ug -> ug.getUser().getId().equals(userId));
        if (alreadyInGroup) {
            throw new RuntimeException("User is already in this group");
        }

        AuthUserGroup authUserGroup = new AuthUserGroup();
        authUserGroup.setUser(user);
        authUserGroup.setGroup(group);
        authUserGroup.setPermissionId(permission);

        authUserGroupRepository.save(authUserGroup);

        return group;
    }

    public void updateGroupName(Integer groupId, String newName) {
        AuthUserPermissionService.checkAdminOrSuperAdminAccess();

        AuthGroup group = authGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + groupId));

        if (authGroupRepository.existsByName(newName)) {
            throw new RuntimeException("Group name already exists.");
        }

        group.setName(newName);
        authGroupRepository.save(group);
    }

    public void removeUserFromGroup(Integer userId, Integer groupId) {
        AuthUserPermissionService.checkAdminOrSuperAdminAccess();

        Optional<UserAuth> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Optional<AuthGroup> groupOptional = authGroupRepository.findById(groupId);
        if (groupOptional.isEmpty()) {
            throw new RuntimeException("Group not found");
        }

        UserAuth user = userOptional.get();
        AuthGroup group = groupOptional.get();

        Optional<AuthUserGroup> userGroupOptional = authUserGroupRepository.findByUserAndGroup(user, group);
        if (userGroupOptional.isEmpty()) {
            throw new RuntimeException("User is not part of this group");
        }

        authUserGroupRepository.delete(userGroupOptional.get());
    }

    public void deleteGroupById(Integer groupId) {
        AuthUserPermissionService.checkSuperAdminAccess();

        AuthGroup group = authGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        authGroupRepository.delete(group);
    }
}
