package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthGroupRepository extends JpaRepository<AuthGroup, Integer> {
    Optional<AuthGroup> findByName(String name);
    Optional<AuthGroup> findById(Integer id);
    boolean existsByName(String name);

    @Query("SELECT g FROM AuthGroup g LEFT JOIN FETCH g.userGroups ug LEFT JOIN FETCH ug.user")
    List<AuthGroup> findAllWithUsers();
}