package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.*;
import com.RulesTV.RulesTV.repositories.UserRepository;
import com.RulesTV.RulesTV.rest.DTO.LoginUserAuthDTO;
import com.RulesTV.RulesTV.rest.DTO.RegisterUserAuthDTO;
import com.RulesTV.RulesTV.services.AuthTokenService;
import com.RulesTV.RulesTV.services.AuthenticationService;
import com.RulesTV.RulesTV.services.JwtService;
import com.RulesTV.RulesTV.services.LogService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class); // SLF4J Logger
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenService authTokenService;
    private final UserRepository userRepository;
    private final LogService logService;

    public AuthenticationController(JwtService jwtService,
                                    AuthenticationService authenticationService,
                                    PasswordEncoder passwordEncoder,
                                    AuthTokenService authTokenService,
                                    UserRepository userRepository,
                                    LogService logService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
        this.authTokenService = authTokenService;
        this.userRepository = userRepository;
        this.logService = logService;
    }

    @PostMapping("/signup")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserAuth> register(@RequestBody RegisterUserAuthDTO registerUserAuthDto) {
        UserAuth registeredUser = authenticationService.signup(registerUserAuthDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Map<String, Object> body, HttpServletRequest request) {
        if (!body.containsKey("email") || !body.containsKey("password") || body.size() != 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new LoginResponse(0, null, null, 0, null, "Request must contain only 'email' and 'password'.", null));
        }

        String email = (String) body.get("email");
        String password = (String) body.get("password");

        try {
            UserAuth authenticatedUser = authenticationService.authenticate(new LoginUserAuthDTO(email, password));

            // Generate the token with role included
            String jwtToken = jwtService.generateToken(authenticatedUser);
            long expirationTime = jwtService.getExpirationTime();

            String userRole = authenticatedUser.getRole().name();
            String userName = authenticatedUser.getFullName();
            Number userId = authenticatedUser.getId();

            // Save the auth token
            AuthToken authToken = new AuthToken(jwtToken, authenticatedUser);
            authTokenService.saveToken(authToken);

            // Save login event
            String sessionId = UUID.randomUUID().toString();
            String userAgent = request.getHeader("User-Agent");
            String ipAddress = request.getRemoteAddr();
            String browser = authenticationService.parseBrowserFromUserAgent(userAgent);
            ZonedDateTime loginTimeLisbon = ZonedDateTime.now(ZoneId.of("Europe/Lisbon"));
            LocalDateTime loginTime = loginTimeLisbon.toLocalDateTime();

            AuthLogin authLogin = new AuthLogin(authenticatedUser, ipAddress, userAgent, sessionId, loginTime, browser, loginTime.plusHours(1));
            logService.saveAuthLogin(authLogin);

            return ResponseEntity.ok(new LoginResponse(userId, userName, jwtToken, expirationTime, authenticatedUser.getEmail(), null, userRole));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(0, null, null, 0, null, "Invalid username or password.", null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String authHeader,
                                                      HttpServletRequest request) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Missing or invalid Authorization header"));
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid token"));
        }

        Optional<UserAuth> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not found"));
        }

        UserAuth authenticatedUser = optionalUser.get();

        authenticationService.invalidateToken(token);

        Optional<AuthLogin> optionalLogin = logService.getLatestLoginForUser(authenticatedUser);
        if (optionalLogin.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No active session found for logout"));
        }

        AuthLogin latestLogin = optionalLogin.get();
        String sessionId = latestLogin.getSessionId();
        LocalDateTime loginTime = latestLogin.getLoginTime();
        ZonedDateTime logoutTimeLisbon = ZonedDateTime.now(ZoneId.of("Europe/Lisbon"));
        LocalDateTime logoutTime = logoutTimeLisbon.toLocalDateTime();

        String formattedDuration = authenticationService.calculateSessionDuration(loginTime,logoutTime);

        String userAgent = request.getHeader("User-Agent");
        String ipAddress = request.getRemoteAddr();
        String browser = authenticationService.parseBrowserFromUserAgent(userAgent);

        AuthLogout authLogout = new AuthLogout(
                authenticatedUser,
                sessionId,
                loginTime,
                logoutTime,
                ipAddress,
                userAgent,
                browser,
                formattedDuration
        );
        logService.saveAuthLogout(authLogout);

        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Successfully logged out"));
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<UserAuth>> getAllUsers(){
        List<UserAuth> allUsers = authenticationService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<UserAuth> getUserByEmail(@PathVariable String email) {
        UserAuth user = authenticationService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/user/update/{id}")
    public ResponseEntity<UserAuth> updateUser(@PathVariable Integer id,@RequestBody RegisterUserAuthDTO updateUserDTO){
        UserAuth updateUser = authenticationService.updateUser(id,updateUserDTO);
        return ResponseEntity.ok(updateUser);
    }


    @PostMapping("/user/promote/{email}")
    public ResponseEntity<UserAuth> promoteUserToAdmin(@PathVariable String email) {
        UserAuth user = authenticationService.promoteUserToAdmin(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/admin/downgrade/{email}")
    public ResponseEntity<UserAuth> downgradeAdminToUser(@PathVariable String email) {
        UserAuth user = authenticationService.downgradeAdminToUser(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/refresh_token")
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

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Integer id) {
        try {
            authenticationService.deleteUserById(id);
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }


    }

