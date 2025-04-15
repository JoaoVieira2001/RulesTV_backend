package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.AuthLogin;
import com.RulesTV.RulesTV.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthLoginRepository extends JpaRepository<AuthLogin, Integer> {
    Optional<AuthLogin> findRecentLoginByUser (UserAuth user);
    Optional<AuthLogin> findTopByUserOrderByLoginTimeDesc(UserAuth user);
    void deleteByUser(UserAuth user);


}
