package com.RulesTV.RulesTV.repositories;

import com.RulesTV.RulesTV.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre,Integer> {

}
