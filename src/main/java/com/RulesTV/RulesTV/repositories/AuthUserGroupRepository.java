package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.AuthGroup;
import com.RulesTV.RulesTV.entity.UserAuth;

import com.RulesTV.RulesTV.entity.AuthUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthUserGroupRepository extends JpaRepository<AuthUserGroup, Integer> {

    // Check if a user is already associated with a group
    boolean existsByUserAndGroup(UserAuth user, AuthGroup group);

    // Get the AuthUserGroup for a user and group
    Optional<AuthUserGroup> findByUserAndGroup(UserAuth user,AuthGroup group);

    // Find all user-group associations for a specific group
    List<AuthUserGroup> findByGroup(AuthGroup group);

    AuthGroup group(AuthGroup group);

    void deleteByUser(UserAuth user);

    List<AuthUserGroup> findByUser(UserAuth user);

}