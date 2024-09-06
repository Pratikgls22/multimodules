package com.ecommerce.jwtsecurity.security;

import com.ecommerce.model.dto.TokenClaims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    private final TokenClaims claims;

    private String secretkey = "";

    private static final String USER_Id = "id";
    private static final String USER_ROLE = "userRole";

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    public JwtTokenProvider(TokenClaims claims) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        this.claims = claims;
    }

    public String createToken(String userEmail,String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("UserName",userName);
        return this.generateSignedJwt(userEmail, claims);
    }


    private String generateSignedJwt(String userEmail, Map<String, Object> claims) {
       return Jwts.builder()
                .setClaims(claims)
                .setSubject(userEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
       return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
            final Claims claims = extractAllClaims(token);
            return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userEmail = extractUserName(token);
        return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Long getUserIdFromToken(String token) {
        return extractClaim(token, claims -> {
            Object userIdObject = claims.get(USER_Id);
            if (userIdObject instanceof Integer) {
                return ((Integer) userIdObject).longValue(); // Convert Integer to Long
            } else if (userIdObject instanceof Long) {
                return (Long) userIdObject;
            } else {
                throw new IllegalArgumentException("User ID is not of type Long or Integer");
            }
        });
    }

    public String getUserNameFromToken(String token){
        return extractClaim(token, claims -> {
            String subject = claims.getSubject();
            return subject;
        });
    }
}
