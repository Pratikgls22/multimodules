package com.ecommerce.security.security.jwt;


import com.ecommerce.utility.enums.CommonEnum;
import com.ecommerce.utility.enums.ExceptionEnum;
import com.ecommerce.utility.enums.JwtExceptionEnum;
import com.ecommerce.utility.exception.CustomException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.ecommerce.utility.enums.CommonEnum.USER_ROLE;

@Component
@Slf4j
public class JwtTokenProvider {

    private final JWSSigner jwsSigner;
    private final JWSVerifier jwsVerifier;

    public JwtTokenProvider() throws JOSEException {
        this.jwsSigner = new MACSigner(CommonEnum.SECRETKEY.getValue().getBytes(StandardCharsets.UTF_8));
        this.jwsVerifier = new MACVerifier(CommonEnum.SECRETKEY.getValue().getBytes(StandardCharsets.UTF_8));
    }

    //  This method is used to resolve and get JWT token from HTTP request's header;
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(CommonEnum.AUTHORIZATION.getValue());
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    //  this method will validate the token
    public Boolean validateToken(String token) {
        this.parseJwtAndExtractClaims(token);
        return this.isTokenExpired(token);
    }

    public Authentication getAuthentication(String token) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(getUserRole(token)));
        UserDetails userDetails = new User(getUsername(token), getUsername(token), true, false, false, false, grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return this.parseJwtAndExtractClaims(token).getSubject();
    }

    public Long getUserIdFromToken(String token) {
        return (Long) this.parseJwtAndExtractClaims(token).getClaim(CommonEnum.USER_ID.getValue());
    }

    public String getUserRole(String token) {
        return (String) this.parseJwtAndExtractClaims(token).getClaim(USER_ROLE.getValue());
    }

    private Boolean isTokenExpired(String token) {
        if (extractExpiration(token).before(new Date())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private Date extractExpiration(String token) {
        return this.parseJwtAndExtractClaims(token).getExpirationTime();
    }

    private JWTClaimsSet parseJwtAndExtractClaims(String jwtToken) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(jwtToken);
            boolean isValid = signedJWT.verify(this.jwsVerifier);
            if (isValid) {
                return signedJWT.getJWTClaimsSet();
            }
            throw new CustomException(ExceptionEnum.INVALID_TOKEN.getValue(), HttpStatus.UNAUTHORIZED);
        } catch (JOSEException | ParseException e) {
            throw new CustomException(ExceptionEnum.INVALID_TOKEN.getValue(), HttpStatus.UNAUTHORIZED);
        }
    }

    private SignedJWT generateSignedJwt(String subject, Map<String, Object> customClaims) {
        try {
            // verify claims & parse to JWTClaimsSet
            var jwtClaimsSet = verifyClaims(customClaims);
            Date now = new Date();
            // validity set for 15 minutes as per standards
            Date validity = new Date(now.getTime() + 900000);
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                    .type(JOSEObjectType.JWT)
                    .build();
            // create payload for token
            JWTClaimsSet payload = new JWTClaimsSet.Builder(jwtClaimsSet)
                    .issuer("jwt-security")
                    .subject(subject)
                    .issueTime(now)
                    .expirationTime(validity)
                    .build();
            SignedJWT signedJWT = new SignedJWT(header, payload);
            signedJWT.sign(this.jwsSigner);
            return signedJWT;
        } catch (JOSEException e) {
            log.error("Exception while creating token ::: ", e);
            throw new CustomException(JwtExceptionEnum.SOMETHING_WENT_WRONG.getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public JWTClaimsSet verifyClaims(Map<String, Object> customClaims) {
        try {
            // check if custom claims are present with key and value
            Map<String, Object> actualClaims = Optional.ofNullable(customClaims)
                    .orElseGet(Collections::emptyMap)
                    .entrySet()
                    .stream()
                    .filter(foundEntry -> List.of(CommonEnum.USER_ID.getValue(), USER_ROLE.getValue()).contains(foundEntry.getKey()))
                    .filter(foundEntry -> Optional.ofNullable(foundEntry.getValue()).isPresent() && Optional.ofNullable(foundEntry.getKey()).isPresent())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            JWTClaimsSet parse = JWTClaimsSet.parse(actualClaims);
            return parse;

        } catch (Exception e) {
            throw new CustomException(JwtExceptionEnum.INVALID_TOKEN.getValue(), HttpStatus.UNAUTHORIZED);
        }
    }

    public String createToken(String username, String userRole,Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CommonEnum.USER_NAME.getValue(), username);
        claims.put(CommonEnum.USER_ROLE.getValue(), userRole);
        claims.put(CommonEnum.USER_ID.getValue(), userId);
        // new with nimbus jose jwt
        return this.generateSignedJwt(username, claims).serialize();
    }

    public String createNewTokenFromToken(String token) {
        var jwtClaimsSet = this.parseJwtAndExtractClaims(token);
        var claims = jwtClaimsSet.getClaims();
        return this.generateSignedJwt(jwtClaimsSet.getSubject(), claims).serialize();
    }
}