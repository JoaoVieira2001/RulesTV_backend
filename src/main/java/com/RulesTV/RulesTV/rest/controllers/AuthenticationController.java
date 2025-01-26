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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class); // SLF4J Logger

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
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

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserAuth>> getAllUsers(){
        List<UserAuth> allUsers = authenticationService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}

