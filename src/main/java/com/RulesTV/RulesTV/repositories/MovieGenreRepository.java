package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieGenreRepository extends JpaRepository<MovieGenre,Integer> {
}
