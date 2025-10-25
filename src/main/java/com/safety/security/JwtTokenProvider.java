package com.safety.security;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Date;


@Component
public class JwtTokenProvider {


@Value("${jwt.secret}")
private String jwtSecret;


@Value("${jwt.expirationMs}")
private int jwtExpirationMs;


public String generateToken(String username, String role) {
Date now = new Date();
Date expiry = new Date(now.getTime() + jwtExpirationMs);
return Jwts.builder()
.setSubject(username)
.claim("role", role)
.setIssuedAt(now)
.setExpiration(expiry)
.signWith(SignatureAlgorithm.HS512, jwtSecret)
.compact();
}


public String getUsernameFromToken(String token) {
return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
}


public boolean validateToken(String token) {
try {
Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
return true;
} catch (JwtException | IllegalArgumentException e) {
return false;
}
}
}