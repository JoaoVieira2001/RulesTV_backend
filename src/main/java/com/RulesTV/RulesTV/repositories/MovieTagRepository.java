package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.MovieTag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieTagRepository extends JpaRepository<MovieTag,Integer> {

}
