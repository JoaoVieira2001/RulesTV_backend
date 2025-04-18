package com.RulesTV.RulesTV.services;
import com.RulesTV.RulesTV.entity.AuthToken;
import com.RulesTV.RulesTV.repositories.AuthTokenRepository;
import com.RulesTV.RulesTV.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthTokenService {

    private final AuthTokenRepository tokenRepository;
    private final UserRepository userRepository;

    public AuthTokenService(AuthTokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }


    public List<AuthToken> getAllTokens() {
        AuthUserPermissionService.checkSuperAdminAccess();
        return tokenRepository.findAll();
    }

    public List<AuthToken> getTokensByUserId(Integer userId){
        AuthUserPermissionService.checkSuperAdminAccess();
        return tokenRepository.findByUserId(userId);
    }

    public void saveToken(AuthToken authToken){
        tokenRepository.save(authToken);
    }

    public boolean deleteToken(String token) {
        AuthUserPermissionService.checkSuperAdminAccess();
        Optional<AuthToken> authTokenOptional = tokenRepository.findByToken(token);
        if (authTokenOptional.isPresent()) {
            tokenRepository.deleteByToken(token);
            return true;
        }
        return false;
    }



}
