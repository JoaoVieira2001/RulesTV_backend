package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAuth, Integer> {
    Optional<UserAuth> findByEmail(String email);
}

