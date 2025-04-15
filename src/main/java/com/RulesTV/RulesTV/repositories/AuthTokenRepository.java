package com.RulesTV.RulesTV.repositories;
import java.util.List;
import java.util.Optional;

import com.RulesTV.RulesTV.entity.AuthToken;
import com.RulesTV.RulesTV.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenRepository extends JpaRepository<AuthToken,Integer> {
    Optional<AuthToken> findByToken(String token);
    List<AuthToken> findByUserId(Integer userId);
    void deleteByToken(String token);
    void deleteByUser(UserAuth user);
}
