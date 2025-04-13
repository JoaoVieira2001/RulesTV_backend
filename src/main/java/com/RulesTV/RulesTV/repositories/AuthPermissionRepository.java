package com.RulesTV.RulesTV.repositories;

import com.RulesTV.RulesTV.entity.AuthPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthPermissionRepository extends JpaRepository<AuthPermission, Integer> {
    boolean existsByCodename(String codename);
    Optional<AuthPermission> findByCodename(String codename);
}