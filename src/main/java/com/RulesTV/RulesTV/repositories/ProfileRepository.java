package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile,Integer> {
}
