package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.PermissionRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PermissionRoleRepository extends JpaRepository<PermissionRole,Integer> {
}
