package com.RulesTV.RulesTV.repositories;

import com.RulesTV.RulesTV.entity.Account;
import com.RulesTV.RulesTV.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    void deleteAccountByEmail (String email);

    boolean existsAccountByEmail (String email);
}