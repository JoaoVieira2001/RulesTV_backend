package com.RulesTV.RulesTV.repositories;

import com.RulesTV.RulesTV.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notifications,Integer> {
    List<Notifications> findByPerformedByEmail(String email);
}
