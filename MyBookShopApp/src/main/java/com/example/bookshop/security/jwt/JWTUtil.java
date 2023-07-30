package com.example.bookshop.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JWTUtil {

    @Value("${auth.secret}")
    private String secret;

    private String createToken(Map<String, Object> aClaims, String aUsername) {
        return Jwts.builder()
                .setClaims(aClaims)
                .setSubject(aUsername)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String generateToken(UserDetails aUserDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, aUserDetails.getUsername());
    }

    public String extractUsername(String aToken) {
        return extractClaim(aToken, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> aClaimsResolver) {
        Claims claims = extractAllClaims(token);
        return aClaimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String aToken) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(aToken)
                .getBody();
    }

    public Date extractExpiration(String aToken) {
        return extractClaim(aToken, Claims::getExpiration);
    }

    public boolean isTokenExpired(String aToken) {
        return extractExpiration(aToken).before(new Date());
    }

    public boolean validateToken(String aToken, UserDetails aUserDetails) {
        String username = extractUsername(aToken);
        return (username.equals(aUserDetails.getUsername()) && !isTokenExpired(aToken));
    }

}

