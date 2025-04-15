package com.RulesTV.RulesTV.services;
import com.RulesTV.RulesTV.entity.AuthLogin;
import com.RulesTV.RulesTV.entity.AuthLogout;
import com.RulesTV.RulesTV.entity.UserAuth;
import com.RulesTV.RulesTV.repositories.AuthLoginRepository;
import com.RulesTV.RulesTV.repositories.AuthLogoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogService {

    private final AuthLoginRepository authLoginRepository;
    private final AuthLogoutRepository authLogoutRepository;

    public LogService(AuthLoginRepository authLoginRepository, AuthLogoutRepository authLogoutRepository) {
        this.authLoginRepository = authLoginRepository;
        this.authLogoutRepository = authLogoutRepository;
    }

    public List<AuthLogin> getAllLogins(){
        return authLoginRepository.findAll();
    }

    public List<AuthLogout> getAllLogouts(){
        return authLogoutRepository.findAll();
    }

    public Optional<AuthLogin> getLatestLoginForUser(UserAuth user){
        return authLoginRepository.findTopByUserOrderByLoginTimeDesc(user);
    }

    public AuthLogin saveAuthLogin(AuthLogin authLogin){
        return authLoginRepository.save(authLogin);
    }

    public AuthLogout saveAuthLogout(AuthLogout authLogout){
        return authLogoutRepository.save(authLogout);
    }
}
