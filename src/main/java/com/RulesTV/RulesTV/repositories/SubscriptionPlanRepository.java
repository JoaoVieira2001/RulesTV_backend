package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan,Integer> {
}
