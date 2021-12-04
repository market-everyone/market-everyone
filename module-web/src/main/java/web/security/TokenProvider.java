package web.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private final String base64Secret;
    private final Long tokenValidityInMilliseconds;

    public TokenProvider(
            @Value("${jwt.base64-secret}") String base64Secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.base64Secret = base64Secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, base64Secret)
                .compact();
    }

    public String getUserEmailFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(base64Secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isValidateToken(String authToken) {
        try {
            log.info("토큰 유효성 검사를 한다.");
            Jwts.parser().setSigningKey(base64Secret).parseClaimsJws(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("유효하지 않은 토큰입니다.");
            log.trace(e.getMessage());
        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰입니다.");
            log.trace(e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 방식의 토큰입니다.");
            log.trace(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.trace(e.getMessage());
        }
        return false;
    }
}
