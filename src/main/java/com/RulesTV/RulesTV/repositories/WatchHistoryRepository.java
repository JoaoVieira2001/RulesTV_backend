package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WatchHistoryRepository extends JpaRepository<WatchHistory,Integer> {
}
