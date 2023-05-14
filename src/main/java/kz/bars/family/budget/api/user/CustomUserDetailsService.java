package kz.bars.family.budget.api.user;

import kz.bars.family.budget.api.JWT.JWTTokenProvider;
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
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        String extractedUsername = jwtTokenProvider.extractUsernameFromToken(token);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            List<GrantedAuthority> authorities = jwtTokenProvider.extractAuthoritiesFromToken(token);
                return new CustomUserDetails(extractedUsername, authorities);
        }
        throw new UsernameNotFoundException("User not found with username: " + extractedUsername);
    }

}
