package my.web.stock.market.stockmarket.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import my.web.stock.market.stockmarket.dto.security.JWTAuthenticationDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JWTService {

    private static final Logger LOGGER = LogManager.getLogger(JWTService.class);

    @Value("d68253f3ed498c2522d189ad2a1463a602fd48d804fa2f119d15fa9a99c2ad2c")
    private String jwtSecret;

    public JWTAuthenticationDTO generateAuthToken(String email) {
        JWTAuthenticationDTO authToken = new JWTAuthenticationDTO();
        authToken.setToken(generateJwtToken(email));
        authToken.setRefreshToken(generateRefreshToken(email));

        return authToken;
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSingInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSingInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getPayload();
            return true;
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JwtException", e);
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported JwtException", e);
        } catch (MalformedJwtException e) {
            LOGGER.error("MalformedJwtException", e);
        } catch (SecurityException e) {
            LOGGER.error("SecurityException", e);
        } catch (Exception e) {
            LOGGER.error("invalid", e);
        }

        return false;
    }

    public JWTAuthenticationDTO refreshBaseToken(String email, String refreshToken) {
        JWTAuthenticationDTO authToken = new JWTAuthenticationDTO();
        authToken.setToken(generateJwtToken(email));
        authToken.setRefreshToken(refreshToken);

        return authToken;
    }

    private String generateJwtToken(String email) {
        Date date = Date.from(LocalDateTime.now().plusHours(16).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(email)
                .expiration(date)
                .signWith(getSingInKey())
                .compact();
    }

    private String generateRefreshToken(String email) {
        Date date = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(email)
                .expiration(date)
                .signWith(getSingInKey())
                .compact();
    }

    private SecretKey getSingInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
