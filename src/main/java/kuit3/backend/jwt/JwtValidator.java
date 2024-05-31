package kuit3.backend.jwt;

import io.jsonwebtoken.*;
import kuit3.backend.common.exception.jwt.bad_request.JwtUnsupportedTokenException;
import kuit3.backend.common.exception.jwt.unauthorized.JwtInvalidTokenException;
import kuit3.backend.common.exception.jwt.unauthorized.JwtMalformedTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static kuit3.backend.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Component
public class JwtValidator {

    @Value("${JWT_SECRET_KEY}")
    private String JWT_SECRET_KEY;

    @Value("${JWT_EXPIRED_IN}")
    private long JWT_EXPIRED_IN;

    public boolean isExpiredToken(String token) throws JwtInvalidTokenException {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY).build()
                    .parseClaimsJws(token);
            return claims.getBody().getExpiration().before(new Date());

        } catch (ExpiredJwtException e) {
            return true;

        } catch (UnsupportedJwtException e) {
            throw new JwtUnsupportedTokenException(UNSUPPORTED_TOKEN_TYPE);
        } catch (MalformedJwtException e) {
            throw new JwtMalformedTokenException(MALFORMED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new JwtInvalidTokenException(INVALID_TOKEN);
        } catch (JwtException e) {
            log.error("[JwtTokenValidator.validateAccessToken]", e);
            throw e;
        }
    }
    public long getLongValueByTokenPayload(String jwt, String payloadKey) throws JwtInvalidTokenException {
        String token = jwt.substring(7);
        return Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody().get(payloadKey, Integer.class);
    }
}
