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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public UserAuth signup(RegisterUserAuthDTO input) {
        if (input.getEmail() == null || input.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (input.getName() == null || input.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (input.getPassword() == null || input.getPassword().isEmpty()) {
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

    public UserAuth getUserByEmail(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("You must be authenticated to view this resource");
        }

        if (authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You are not authorized to view this resource");
        }

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

    }


    public UserAuth promoteUserToAdmin(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("You must be authenticated to view this resource");
        }

        if (authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You are not authorized to view this resource");
        }

        UserAuth user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        if ("ADMIN".equals(user.getRole())) {
            throw new RuntimeException("User is already an admin.");
        }

        user.setRole("ADMIN");
        return userRepository.save(user);
    }


    public UserAuth downgradeUserToAdmin(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("You must be authenticated to view this resource");
        }

        if (authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException("You are not authorized to view this resource");
        }

        UserAuth user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        if ("USER".equals(user.getRole())) {
            throw new RuntimeException("User is already an user.");
        }

        user.setRole("USER");
        return userRepository.save(user);
    }


    public Map<String, String> refreshToken(String oldToken) {
        String userEmail = jwtService.extractUsername(oldToken);

        if (userEmail == null) {
            throw new RuntimeException("Invalid token: Cannot extract user information");
        }

        UserAuth user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        if (!jwtService.isTokenValid(oldToken, user)) {
            throw new RuntimeException("Invalid or expired token");
        }
        String newToken = jwtService.generateToken(user);

        Map<String, String> response = new HashMap<>();
        response.put("email", userEmail);
        response.put("old_token", oldToken);
        response.put("new_token", newToken);

        return response;
    }


    public Map<String, Object> verifyToken(String token) {
        Map<String, Object> response = new HashMap<>();

        try {
            String userEmail = jwtService.extractUsername(token);

            if (userEmail == null) {
                response.put("error", "Invalid token: Unable to extract user information");
                return response;
            }
            UserAuth user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

            boolean isValid = jwtService.isTokenValid(token, user);

            if (!isValid) {
                response.put("error", "Token is invalid or expired");
                return response;
            }

            response.put("email", userEmail);
            response.put("role", user.getRole());
            response.put("valid", true);
            response.put("message", "Token is valid");

        } catch (Exception e) {
            response.put("error", "Invalid or expired token");
        }

        return response;
    }
}


