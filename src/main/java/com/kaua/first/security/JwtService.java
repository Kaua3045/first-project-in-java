package com.kaua.first.security;

import com.kaua.first.entities.PersonEntity;
import com.kaua.first.security.interfaces.JwtServiceGateway;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtService implements JwtServiceGateway {

    private String SECRET_KEY_JWT;

    public JwtService(String SECRET_KEY_JWT) {
        this.SECRET_KEY_JWT = SECRET_KEY_JWT;
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(PersonEntity person) {
        return generateToken(new HashMap<>(), person);
    }

    public String generateToken(Map<String, Object> extraClaims, PersonEntity Person) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(Person.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, PersonEntity person) {
        String username = extractUsername(token);
        return (username.equals(person.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY_JWT);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
