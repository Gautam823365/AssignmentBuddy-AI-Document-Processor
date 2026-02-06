package com.example.assignment_buddy_gateway.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final String SECRET = "my-super-secret-key-that-should-be-very-long";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hours

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()

                .parseClaimsJws(token)
                .getBody();
    }

    public void validateToken(String token) {
        extractAllClaims(token);
    }
}
