package com.project.management.authentication.jwt;

import com.project.management.role.domain.Role;
import com.project.management.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class JwtTokenProvider {


    private final String secretKey;
    private final long validityInMilliSeconds;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String secretKey,
                            @Value("${security.jwt.token.expiration}") long validityInMilliSeconds) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliSeconds = validityInMilliSeconds;
    }

    /**
     * Create JWT string given user name and role
     *
     * @param user object
     * @return String encrypted token
     */
    public String createToken(User user) {

        Claims claims = Jwts.claims().setSubject(user.getUserName());
        Set<String> roles = new HashSet<>();

        user.getRoles().forEach(role -> roles.add(role.getRoleName()));

        claims.put("roles", roles);

        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + validityInMilliSeconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }


    /**
     * Validate the JWT String
     *
     * @param token jwt
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserName(String token) {
        return getTokenBody(token).getSubject();
    }

    public Set<Role> getRoles(String token) {
        return getTokenBody(token).get("roles", Set.class);
    }


    private Claims getTokenBody(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();
    }


}
