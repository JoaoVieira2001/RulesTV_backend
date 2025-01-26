package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.Library;
import com.RulesTV.RulesTV.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibraryRepository extends JpaRepository<Library,Integer> {
}
