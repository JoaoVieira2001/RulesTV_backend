package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.AuthUserPermission;
import com.RulesTV.RulesTV.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthUserPermissionRepository extends JpaRepository<AuthUserPermission, Integer>  {
    Optional<AuthUserPermission> findByUserIdAndPermissionId(Integer userId, Integer permissionId);
    List<AuthUserPermission> findAllByUser(UserAuth user);
}
