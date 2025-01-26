package com.RulesTV.RulesTV.repositories;
import com.RulesTV.RulesTV.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {

}
