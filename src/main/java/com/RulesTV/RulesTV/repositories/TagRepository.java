package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Integer> {
}
