package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.rest.DTO.AccountDTO;
import com.RulesTV.RulesTV.rest.DTO.RoleDTO;
import org.springframework.http.HttpStatus;
import com.RulesTV.RulesTV.entity.Account;
import com.RulesTV.RulesTV.repositories.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @GetMapping("/all")
    public List<AccountDTO> getAllAccount(){
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream().map(account -> {
            AccountDTO dto = new AccountDTO(account.getEmail(),account.getName(),account.getPassword(),account.getProfilePicture(),account.getSignupDate(),account.getCreatedAt(),account.getUpdatedAt(),null,account.getSubscriptionPlan().getSubscription_plan_type().name());
            dto.setEmail(account.getEmail());
            dto.setName(account.getName());
            dto.setPassword(account.getPassword());
            dto.setCreatedAt(account.getCreatedAt());
            dto.setUpdatedAt(account.getUpdatedAt());
            dto.setProfilePicture(account.getProfilePicture());
            dto.setSignupDate(account.getSignupDate());
            dto.setSubscription_type(account.getSubscriptionPlan().getSubscription_plan_type().name());

            if (account.getRole() != null) {
                RoleDTO roleDTO = new RoleDTO(account.getRole().getId(),account.getRole().getName(),account.getRole().getTypeRole().name());
                roleDTO.setId(account.getRole().getId());
                roleDTO.setName(account.getRole().getName());
                roleDTO.setTypeRole(account.getRole().getTypeRole().name());
                dto.setRole(roleDTO);
            }
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{email}")
    public ResponseEntity<AccountDTO> getAccountByEmail(@PathVariable String email){
        return accountRepository.findByEmail(email)
                .map(account -> {
                    AccountDTO dto = new AccountDTO(account.getEmail(),account.getName(),account.getPassword(),account.getProfilePicture(),account.getSignupDate(),account.getCreatedAt(),account.getUpdatedAt(), null,account.getSubscriptionPlan().getSubscription_plan_type().name());
                    dto.setEmail(account.getEmail());
                    dto.setName(account.getName());
                    dto.setPassword(account.getPassword());
                    dto.setCreatedAt(account.getCreatedAt());
                    dto.setUpdatedAt(account.getUpdatedAt());
                    dto.setProfilePicture(account.getProfilePicture());
                    dto.setSignupDate(account.getSignupDate());
                    dto.setSubscription_type(account.getSubscriptionPlan().getSubscription_plan_type().name());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/post")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        Account savedAccount = accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    @PutMapping("/put/{email}")
    public ResponseEntity<Account> updateAccount(@PathVariable String email, @RequestBody Account updatedAccount) {
        return accountRepository.findByEmail(email)
                .map(existingAccount -> {
                    existingAccount.setName(updatedAccount.getName());
                    existingAccount.setPassword(updatedAccount.getPassword());
                    existingAccount.setProfilePicture(updatedAccount.getProfilePicture());
                    existingAccount.setSignupDate(updatedAccount.getSignupDate());
                    existingAccount.setCreatedAt(updatedAccount.getCreatedAt());
                    existingAccount.setUpdatedAt(updatedAccount.getUpdatedAt());
                    existingAccount.setRole(updatedAccount.getRole());
                    existingAccount.setSubscriptionPlan(updatedAccount.getSubscriptionPlan());
                    accountRepository.save(existingAccount);
                    return ResponseEntity.ok(existingAccount);
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String email){
        if(accountRepository.existsAccountByEmail(email)){
            accountRepository.deleteAccountByEmail(email);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
