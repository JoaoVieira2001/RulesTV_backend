package com.RulesTV.RulesTV.configs;
import com.RulesTV.RulesTV.entity.UserAuth;
import com.RulesTV.RulesTV.repositories.UserRepository;
import com.RulesTV.RulesTV.services.JwtService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public CustomAuthenticationProvider(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        UserAuth userAuth = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!jwtService.isTokenValid(authentication.getCredentials().toString(), userAuth)) {
            throw new RuntimeException("Invalid JWT Token");
        }

        UserDetails userDetails = User.builder()
                .username(userAuth.getEmail())
                .password(userAuth.getPassword())
                .authorities(userAuth.getAuthorities())
                .build();

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
