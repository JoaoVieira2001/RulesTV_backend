package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.AuthLogout;
import com.RulesTV.RulesTV.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthLogoutRepository extends JpaRepository<AuthLogout, Integer> {

    void deleteByUser(UserAuth user);

    List<AuthLogout> findByUser(UserAuth user);
}