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
    public List<AuthGroup> listAllGroups() {
        return authGroupService.getAllGroups();
    }

    // Get all users of a specific group
    @GetMapping("/users/group/{groupName}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserAuth> getUsersByGroup(@PathVariable String groupName){
        return authGroupService.getUsersInGroup(groupName);
    }

    // Get a group by its name
    @GetMapping("/group/{groupName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthGroup> getGroupByName(@PathVariable String groupName){
        try {
            AuthGroup group = authGroupService.getGroupByName(groupName);
            return ResponseEntity.ok(group);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new group
    @PostMapping("/group/post")
    @PreAuthorize("hasRole('ADMIN')")
    public AuthGroup createGroup(@RequestBody AuthGroupDTO authGroupDTO){
        return authGroupService.createGroup(authGroupDTO.getGroupName());
    }

    // Add a user to a group
    @PostMapping("/user/group/post")
    @PreAuthorize("hasRole('ADMIN')")
    public String addUserToGroup(@RequestBody UserGroupRequestDTO requestDto) {
        try {
            authGroupService.addUserToGroup(requestDto.getUserId(), requestDto.getGroupName());
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
