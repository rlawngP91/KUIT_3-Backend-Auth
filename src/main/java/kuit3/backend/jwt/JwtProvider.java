package kuit3.backend.jwt;

import io.jsonwebtoken.*;
import kuit3.backend.common.exception.jwt.unauthorized.JwtInvalidTokenException;
import kuit3.backend.common.exception.jwt.unauthorized.JwtMalformedTokenException;
import kuit3.backend.common.exception.jwt.bad_request.JwtUnsupportedTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Component
public class JwtProvider {

    @Value("${JWT_SECRET_KEY}")
    private String JWT_SECRET_KEY;

    @Value("${JWT_EXPIRED_IN}")
    private long JWT_EXPIRED_IN;

    public String createToken(String principal, long userId) {
        log.info("JWT key={}", JWT_SECRET_KEY);

        Claims claims = Jwts.claims().setSubject(principal);
        Date now = new Date();
        Date validity = new Date(now.getTime() + JWT_EXPIRED_IN);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public String getPrincipal(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
