package com.RulesTV.RulesTV.services;

import com.RulesTV.RulesTV.entity.AuthGroup;
import com.RulesTV.RulesTV.entity.AuthUserGroup;
import com.RulesTV.RulesTV.entity.UserAuth;
import com.RulesTV.RulesTV.repositories.AuthGroupRepository;
import com.RulesTV.RulesTV.repositories.AuthUserGroupRepository;
import com.RulesTV.RulesTV.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthGroupService {

    private final AuthGroupRepository authGroupRepository;
    private final AuthUserGroupRepository authUserGroupRepository;
    private final UserRepository userRepository;

    public AuthGroupService(AuthGroupRepository authGroupRepository,
                            AuthUserGroupRepository authUserGroupRepository,
                            UserRepository userRepository) {
        this.authGroupRepository = authGroupRepository;
        this.authUserGroupRepository = authUserGroupRepository;
        this.userRepository = userRepository;
    }

    public List<AuthGroup> getAllGroups() {
        return authGroupRepository.findAll();
    }

    public AuthGroup createGroup(String groupName) {
        if (authGroupRepository.findByName(groupName).isPresent()) {
            throw new IllegalArgumentException("Group with this name already exists");
        }
        AuthGroup group = new AuthGroup();
        group.setName(groupName);
        return authGroupRepository.save(group);
    }

    public AuthGroup addUserToGroup(Integer userId, String groupName) {
        Optional<UserAuth> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Optional<AuthGroup> groupOptional = authGroupRepository.findByName(groupName);
        if (groupOptional.isEmpty()) {
            throw new RuntimeException("Group not found");
        }

        AuthGroup group = groupOptional.get();

        if (group.getUserGroups().size() >= 6) {
            throw new RuntimeException("Group has reached its maximum user limit (6)");
        }

        boolean alreadyInGroup = group.getUserGroups().stream()
                .anyMatch(ug -> ug.getUser().getId().equals(userId));
        if (alreadyInGroup) {
            throw new RuntimeException("User is already in this group");
        }

        AuthUserGroup authUserGroup = new AuthUserGroup();
        authUserGroup.setUser(userOptional.get());
        authUserGroup.setGroup(group);

        authUserGroupRepository.save(authUserGroup);

        return group;
    }



    public void removeUserFromGroup(Integer userId, Integer groupId){
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

       Optional<AuthUserGroup> userGroupOptional = authUserGroupRepository.findByUserAndGroup(user,group);
       if (userGroupOptional.isEmpty()) {
           throw new RuntimeException("User is not part of this group");
       }

       authUserGroupRepository.delete(userGroupOptional.get());
    }

    // Get all users of a specific group
    public List<UserAuth> getUsersInGroup(String groupName){
        Optional<AuthGroup> groupOptional = authGroupRepository.findByName(groupName);
        if (groupOptional.isEmpty()) {
            throw new RuntimeException("Group not found");
        }

        AuthGroup group = groupOptional.get();
        return authUserGroupRepository.findByGroup(group).stream()
                .map(AuthUserGroup::getUser)
                .toList();
    }

    //Get a group by its name
    public AuthGroup getGroupByName(String groupName){
        return authGroupRepository.findByName(groupName)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }


}
