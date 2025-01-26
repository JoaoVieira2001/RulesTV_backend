package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SerieRepository extends JpaRepository<Serie,Integer> {
}
