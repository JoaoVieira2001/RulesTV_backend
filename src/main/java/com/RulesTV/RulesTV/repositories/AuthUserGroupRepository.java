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

    boolean existsByUserAndGroup(UserAuth user, AuthGroup group);

    Optional<AuthUserGroup> findByUserAndGroup(UserAuth user,AuthGroup group);

    List<AuthUserGroup> findByGroup(AuthGroup group);

    AuthGroup group(AuthGroup group);

    void deleteByUser(UserAuth user);

    List<AuthUserGroup> findByUser(UserAuth user);

}