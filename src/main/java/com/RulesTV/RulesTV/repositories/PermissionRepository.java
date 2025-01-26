package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Integer> {

}
