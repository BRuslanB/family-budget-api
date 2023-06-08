package kz.bars.family.budget.api.user;

import kz.bars.family.budget.api.JWT.JWTTokenProvider;
import kz.bars.family.budget.api.exeption.UserNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final JWTTokenProvider jwtTokenProvider;

    public CustomUserDetailsService(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    //нужно пересмотреть логику этого метода для данного приложения
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        try {
            String extractedUsername = jwtTokenProvider.extractUsernameFromToken(token);
            List<GrantedAuthority> authorities = jwtTokenProvider.extractAuthoritiesFromToken(token);
            return new CustomUserDetails(extractedUsername, authorities);
        } catch (Exception ex) {
            throw new UserNotFoundException("User not found");
        }
    }

}
