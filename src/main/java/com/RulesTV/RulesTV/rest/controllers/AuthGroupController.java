package com.RulesTV.RulesTV.rest.controllers;

import com.RulesTV.RulesTV.entity.AuthGroup;
import com.RulesTV.RulesTV.entity.UserAuth;
import com.RulesTV.RulesTV.rest.DTO.AuthGroupDTO;
import com.RulesTV.RulesTV.rest.DTO.UserGroupRequestDTO;
import com.RulesTV.RulesTV.services.AuthGroupService;
import com.RulesTV.RulesTV.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<AuthGroupDTO> getAllGroups() {
        return authGroupService.getAllGroups();
    }

    // Get all users of a specific group
    @GetMapping("/users/group/{groupName}")
    public List<UserAuth> getUsersByGroup(@PathVariable String groupName){
        return authGroupService.getUsersInGroup(groupName);
    }

    // Get a group by its id
    @GetMapping("/group/{groupId}")
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
    public AuthGroup createGroup(@RequestBody AuthGroupDTO authGroupDTO){
        return authGroupService.createGroup(authGroupDTO.getName());
    }

    // Add a user to a group
    @PostMapping("/user/group/post")
    public ResponseEntity<String> addUserToGroup(@RequestBody UserGroupRequestDTO requestDto) {
        try {
            authGroupService.addUserToGroup(requestDto.getUserId(), requestDto.getGroupName());
            return ResponseEntity.ok("User added to group successfully");
        } catch (RuntimeException e) {
            if ("User is already in this group".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // 409
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // 400
        }
    }

    // Update group name
    @PutMapping("/group/{groupId}/put")
    public ResponseEntity<Map<String, String>> updateGroupName(@PathVariable Integer groupId, @RequestBody AuthGroupDTO updatedGroup) {
        Map<String, String> response = new HashMap<>();
        try {
            authGroupService.updateGroupName(groupId, updatedGroup.getName());
            response.put("message", "Group name updated successfully.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Remove a user from a group
    @DeleteMapping("/user/{userId}/group/{groupId}/delete")
    public String removeUserFromGroup(@PathVariable Integer userId, @PathVariable Integer groupId) {
        try {
            authGroupService.removeUserFromGroup(userId, groupId);
            return "User removed from group successfully";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    // Delete a group by ID
    @DeleteMapping("/group/{groupId}/delete")
    public ResponseEntity<String> deleteGroup(@PathVariable Integer groupId) {
        try {
            authGroupService.deleteGroupById(groupId);
            return ResponseEntity.ok("Group deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to delete group: " + e.getMessage());
        }
    }
}
