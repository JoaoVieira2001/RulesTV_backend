package com.RulesTV.RulesTV.services;

import com.RulesTV.RulesTV.entity.UserAuth;
import com.RulesTV.RulesTV.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserAuth userAuth = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return User.builder()
                .username(userAuth.getEmail())
                .password(userAuth.getPassword())
                .authorities(userAuth.getAuthorities())
                .build();
    }
}
