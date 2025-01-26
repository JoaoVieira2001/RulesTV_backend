package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Integer> {

}
