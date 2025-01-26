package com.RulesTV.RulesTV.services;

import com.RulesTV.RulesTV.entity.UserAuth;
import com.RulesTV.RulesTV.repositories.UserRepository;
import com.RulesTV.RulesTV.rest.DTO.LoginUserAuthDTO;
import com.RulesTV.RulesTV.rest.DTO.RegisterUserAuthDTO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public UserAuth signup(RegisterUserAuthDTO input) {
        if(input.getEmail() == null || input.getEmail().isEmpty()){
            throw new IllegalArgumentException("Email is required");
        }

        if(input.getName() == null || input.getName().isEmpty()){
            throw new IllegalArgumentException("Name is required");
        }

        if(input.getPassword() == null || input.getPassword().isEmpty()){
            throw new IllegalArgumentException("Email is required");
        }

        UserAuth user = new UserAuth();
        user.setFullName(input.getName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole("USER");

        return userRepository.save(user);
    }

    public UserAuth authenticate(LoginUserAuthDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserAuth> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("You must be authenticated to view this resource");
        }

        if (authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You are not authorized to view this resource");
        }

        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
