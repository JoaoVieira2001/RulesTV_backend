package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.AuthToken;
import com.RulesTV.RulesTV.services.AuthTokenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/auth/token")
public class AuthTokenController {

    private final AuthTokenService authTokenService;

    public AuthTokenController(AuthTokenService authTokenService) {
        this.authTokenService = authTokenService;
    }

    @GetMapping("/all")
    public List<AuthToken> listAllTokens() {
        return authTokenService.getAllTokens();
    }

    @GetMapping("/user/{userId}")
    public List<AuthToken> getTokensByUser(@PathVariable Integer userId) {
        return authTokenService.getTokensByUserId(userId);
    }

    @DeleteMapping("/delete/{token}")
    public String deleteToken(@PathVariable String token) {
        boolean deleted = authTokenService.deleteToken(token);
        return deleted ? "Token deleted" : "Token not found";
    }
}
