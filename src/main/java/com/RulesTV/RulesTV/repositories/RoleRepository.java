package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
