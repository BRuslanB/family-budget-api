package kz.bars.family.budget.api.user;

import kz.bars.family.budget.api.JWT.JWTTokenProvider;
import kz.bars.family.budget.api.exeption.UserNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final JWTTokenProvider jwtTokenProvider;

    public CustomUserDetailsService(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        try {
            String extractedUsername = jwtTokenProvider.extractUsernameFromToken(token);
            List<GrantedAuthority> authorities = jwtTokenProvider.extractAuthoritiesFromToken(token);

            log.debug("!Authentication User name: " + extractedUsername);
            log.debug("!Authentication User roles: " + authorities);

            return new CustomUserDetails(extractedUsername, authorities);

        } catch (Exception ex) {

            log.debug("!User not found");
            throw new UserNotFoundException("User not found");
        }
    }

}
