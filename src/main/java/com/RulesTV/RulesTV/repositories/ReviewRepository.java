package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
}
