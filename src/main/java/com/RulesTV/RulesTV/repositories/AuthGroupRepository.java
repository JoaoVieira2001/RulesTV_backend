package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthGroupRepository extends JpaRepository<AuthGroup, Integer> {
    Optional<AuthGroup> findByName(String name);

    Optional<AuthGroup> findById(Integer id);
    boolean existsByName(String name);
}