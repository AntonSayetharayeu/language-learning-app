package learningapp.security.jwt;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class JwtUtils {
    //TODO Move this config to a proper place
    private final String SECRET = "^c75@JVG*NZ79%pc#c338@2%*Y69*7h%";
    private final int EXPIRATION_MS = 86400000;

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(getSigningKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getJwtParser().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateToken(String token) {
        getJwtParser().parseSignedClaims(token);
        return true;
    }

    private Key getSigningKey() {
        byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private JwtParser getJwtParser() {
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build();
    }
}
