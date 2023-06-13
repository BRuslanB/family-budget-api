package kz.bars.family.budget.api.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import kz.bars.family.budget.api.exeption.TokenExpiredException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JWTTokenProvider {

    public String extractUsernameFromToken(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (TokenExpiredException ex) {
            // Handling an exception for an expired or erroneous token
            throw new TokenExpiredException("Token has expired or invalid token");
        }
    }

    public Date extractExpirationTimeFromToken(String theToken) {
        return extractClaim(theToken, Claims::getExpiration);
    }

    public Boolean validateToken(String theToken) {
        try {
            return !isTokenExpired(theToken);
        } catch (TokenExpiredException ex) {
            // Handling an exception for an expired or erroneous token
            return false;
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (TokenExpiredException ex) {
            // Handling an exception for an expired or erroneous token
            throw new TokenExpiredException("Token has expired or invalid token");
        }
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignedKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | MalformedJwtException ex) {
            // Handling an exception for an expired or erroneous token
            throw new TokenExpiredException("Token has expired or invalid token");
        }
    }

    private boolean isTokenExpired(String theToken) {
        return extractExpirationTimeFromToken(theToken).before(new Date());
    }

    private Key getSignedKey() {
        byte[] keyByte = Base64.getEncoder().encode(JWTSecurityConstants.SECRET_KEY.getBytes());
        return Keys.hmacShaKeyFor(keyByte);
    }

    public List<GrantedAuthority> extractAuthoritiesFromToken(String token) {
        final Claims claims = extractAllClaims(token);
        List<String> authorities = claims.get("authorities", List.class);
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
