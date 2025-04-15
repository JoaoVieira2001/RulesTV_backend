package com.RulesTV.RulesTV.services;
import com.RulesTV.RulesTV.repositories.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.RulesTV.RulesTV.entity.UserAuth;
import com.RulesTV.RulesTV.rest.DTO.LoginUserAuthDTO;
import com.RulesTV.RulesTV.rest.DTO.RegisterUserAuthDTO;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthTokenRepository authTokenRepository;
    private final AuthLogoutRepository authLogoutRepository;
    private final AuthUserGroupRepository authUserGroupRepository;
    private final JwtService jwtService;
    private final Set<String> blacklistedTokens = new HashSet<>();
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final AuthLoginRepository authLoginRepository;


    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager,
                                 AuthTokenRepository authTokenRepository,
                                 AuthLogoutRepository authLogoutRepository,
                                 AuthUserGroupRepository authUserGroupRepository,
                                 @Lazy JwtService jwtService, AuthLoginRepository authLoginRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.authTokenRepository = authTokenRepository;
        this.authLogoutRepository = authLogoutRepository;
        this.authUserGroupRepository = authUserGroupRepository;
        this.jwtService = jwtService;
        this.authLoginRepository = authLoginRepository;
    }

    public UserAuth signup(RegisterUserAuthDTO input) {
        if (input.getEmail() == null || input.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (input.getName() == null || input.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (input.getPhone_number() == null || input.getPhone_number().isEmpty()) {
            throw new IllegalArgumentException("PhoneNumber is required");
        }

        if (input.getPassword() == null || input.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }

        UserAuth user = new UserAuth();
        user.setFullName(input.getName());
        user.setEmail(input.getEmail());
        user.setPhone_number(input.getPhone_number());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        String role = input.getRole();
        if ("ADMIN".equalsIgnoreCase(role)) {
            user.setRole("ADMIN");
        } else {
            user.setRole("USER");
        }

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

    public UserAuth updateUser(Integer id,RegisterUserAuthDTO updateUserDTO){
        UserAuth user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (updateUserDTO.getName() != null && !updateUserDTO.getName().isEmpty()) {
            user.setFullName(updateUserDTO.getName());
        }
        if (updateUserDTO.getPhone_number() != null && !updateUserDTO.getPhone_number().isEmpty()) {
            user.setPhone_number(updateUserDTO.getPhone_number());
        }
        if (updateUserDTO.getEmail() != null && !updateUserDTO.getEmail().isEmpty()) {
            user.setEmail(updateUserDTO.getEmail());
        }
        if (updateUserDTO.getPassword() != null && !updateUserDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        }

        return userRepository.save(user);

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

    @Transactional
    public boolean deleteUserById(Integer id) {
        Optional<UserAuth> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return false;
        }

        UserAuth user = userOptional.get();

        // Delete associated foreign key entries
        authLogoutRepository.deleteByUser(user);
        authLoginRepository.deleteByUser(user);
        authUserGroupRepository.deleteByUser(user);
        authTokenRepository.deleteByUser(user);

        userRepository.delete(user);

        return true;
    }



    //Store invalidated token
    public void invalidateToken(String token){

        blacklistedTokens.add(token);
    }

    //Validates invalidated token
    public boolean isTokenBlackListed(String token){
        return blacklistedTokens.contains(token);
    }


    public String parseBrowserFromUserAgent(String userAgent){
        if(userAgent == null) return "Unknown";

        userAgent = userAgent.toLowerCase();
        logger.info("Parsing User-Agent: {}", userAgent);
        if (userAgent.contains("edg")) return "Edge";
        if (userAgent.contains("chrome")) return "Chrome";
        if (userAgent.contains("firefox")) return "Firefox";
        if (userAgent.contains("safari")) return "Safari";
        if (userAgent.contains("msie") || userAgent.contains("trident")) return "Internet Explorer";
        return "Other";
    }

    public String calculateSessionDuration(LocalDateTime loginTime, LocalDateTime logoutTime){
        Duration sessionDuration = Duration.between(loginTime,logoutTime);

        return String.format("%02d:%02d:%02d",
                sessionDuration.toHours(),
                sessionDuration.toMinutesPart(),
                sessionDuration.toSecondsPart());
    }
}


