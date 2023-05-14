package kz.bars.family.budget.api.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@AllArgsConstructor
@Log4j2
//@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    final JWTTokenProvider jwtTokenProvider;
    final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(JWTSecurityConstants.HEADER_STRING);
        String token = null;
        String userName = null;

        if (authHeader != null && authHeader.startsWith(JWTSecurityConstants.TOKEN_PREFIX)) {
            token = authHeader.substring(7);
            userName = jwtTokenProvider.extractUsernameFromToken(token);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(token);

            if (jwtTokenProvider.validateToken(token)) {
                var authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                log.debug("!Authentication Token=" + SecurityContextHolder.getContext().getAuthentication().getName());
                Set<String> roles = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                log.debug("!Authentication User roles: " + roles);

            }
        }

        filterChain.doFilter(request, response);
    }

}
