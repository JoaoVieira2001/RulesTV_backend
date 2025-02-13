package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.LoginResponse;
import com.RulesTV.RulesTV.entity.UserAuth;
import com.RulesTV.RulesTV.rest.DTO.LoginUserAuthDTO;
import com.RulesTV.RulesTV.rest.DTO.RegisterUserAuthDTO;
import com.RulesTV.RulesTV.services.AuthenticationService;
import com.RulesTV.RulesTV.services.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class); // SLF4J Logger
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAuth> register(@RequestBody RegisterUserAuthDTO registerUserAuthDto) {
        UserAuth registeredUser = authenticationService.signup(registerUserAuthDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Map<String, Object> body) {
        if (!body.containsKey("email") || !body.containsKey("password") || body.size() != 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LoginResponse(null, 0, "Request must contain only 'email' and 'password'."));
        }

        String email = (String) body.get("email");
        String password = (String) body.get("password");

        try {
            UserAuth authenticatedUser = authenticationService.authenticate(new LoginUserAuthDTO(email, password));
            String jwtToken = jwtService.generateToken(authenticatedUser);
            long expirationTime = jwtService.getExpirationTime();
            return ResponseEntity.ok(new LoginResponse(jwtToken, expirationTime, null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, 0, "Invalid username or password."));
        }
    }

    @GetMapping("/user/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserAuth>> getAllUsers(){
        List<UserAuth> allUsers = authenticationService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/user/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAuth> getUserByEmail(@PathVariable String email) {
        UserAuth user = authenticationService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/user/promote/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAuth> promoteUserToAdmin(@PathVariable String email) {
        UserAuth user = authenticationService.promoteUserToAdmin(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/admin/downgrade/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAuth> downgradeUserToAdmin(@PathVariable String email) {
        UserAuth user = authenticationService.downgradeUserToAdmin(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/refresh_token")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Missing or invalid Authorization header"));
        }

        String oldToken = authHeader.substring(7); // Remove "Bearer " prefix

        try {
            Map<String, String> tokenDetails = authenticationService.refreshToken(oldToken);
            logger.info("Token Refreshed: Email = {}, Old Token = {}, New Token = {}",
                    tokenDetails.get("email"), tokenDetails.get("old_token"), tokenDetails.get("new_token"));

            return ResponseEntity.ok(tokenDetails);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid or expired token"));
        }
    }

    @GetMapping("/user_token")
    public ResponseEntity<Map<String, String>> getAuthenticatedUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized: No authentication found"));
        }

        String userEmail = authentication.getName();

        return ResponseEntity.ok(Map.of("email", userEmail));
    }

    @GetMapping("/verify_token")
    public ResponseEntity<Map<String, Object>> verifyToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Missing or invalid Authorization header"));
        }

        String token = authHeader.substring(7);
        Map<String, Object> verificationResult = authenticationService.verifyToken(token);

        if (verificationResult.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(verificationResult);
        }

        return ResponseEntity.ok(verificationResult);
    }











}

