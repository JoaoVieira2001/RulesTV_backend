package com.RulesTV.RulesTV.rest.controllers;

import com.RulesTV.RulesTV.entity.AuthGroup;
import com.RulesTV.RulesTV.entity.UserAuth;
import com.RulesTV.RulesTV.rest.DTO.AuthGroupDTO;
import com.RulesTV.RulesTV.rest.DTO.UserGroupRequestDTO;
import com.RulesTV.RulesTV.services.AuthGroupService;
import com.RulesTV.RulesTV.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthGroupController {

    private final AuthGroupService authGroupService;
    private final AuthenticationService authenticationService;

    public AuthGroupController(AuthGroupService authGroupService,
                               AuthenticationService authenticationService) {
        this.authGroupService = authGroupService;
        this.authenticationService = authenticationService;
    }

    // Get all groups
    @GetMapping("/group/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AuthGroupDTO> listAllGroups() {
        return authGroupService.getAllGroups().stream()
                .map(group -> new AuthGroupDTO(group.getId(), group.getName()))
                .toList();
    }

    // Get all users of a specific group
    @GetMapping("/users/group/{groupName}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserAuth> getUsersByGroup(@PathVariable String groupName){
        return authGroupService.getUsersInGroup(groupName);
    }

    // Get a group by its id
    @GetMapping("/group/{groupId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthGroupDTO> getGroupById(@PathVariable Integer groupId){
        try {
            AuthGroupDTO groupDTO = authGroupService.getGroupById(groupId);
            return ResponseEntity.ok(groupDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new group
    @PostMapping("/group/post")
    @PreAuthorize("hasRole('ADMIN')")
    public AuthGroup createGroup(@RequestBody AuthGroupDTO authGroupDTO){
        return authGroupService.createGroup(authGroupDTO.getName());
    }

    // Add a user to a group
    @PostMapping("/user/group/post")
    @PreAuthorize("hasRole('ADMIN')")
    public String addUserToGroup(@RequestBody UserGroupRequestDTO requestDto) {
        try {
            authGroupService.addUserToGroup(requestDto.getUserId(), requestDto.getGroupName(),requestDto.getPermissionId());
            return "User added to group successfully";
        } catch (DataIntegrityViolationException e) {
            return "User is already in this group.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    // Remove a user from a group
    @DeleteMapping("/user/{userId}/group/{groupId}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String removeUserFromGroup(@PathVariable Integer userId, @PathVariable Integer groupId) {
        try {
            authGroupService.removeUserFromGroup(userId, groupId);
            return "User removed from group successfully";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
