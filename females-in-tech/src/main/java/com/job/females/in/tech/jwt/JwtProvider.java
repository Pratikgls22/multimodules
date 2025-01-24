package com.job.females.in.tech.jwt;

import com.job.females.in.tech.enums.CommonEnum;
import com.job.females.in.tech.enums.ExceptionEnum;
import com.job.females.in.tech.enums.JwtExceptionEnum;
import com.job.females.in.tech.exception.CustomException;
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

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.job.females.in.tech.enums.CommonEnum.USER_ROLE;

@Component
@Slf4j
public class JwtProvider {

    private final JWSSigner jwsSigner;
    private final JWSVerifier jwsVerifier;

    //SecretKet for signing and verifying ::
    public JwtProvider() throws JOSEException {
        this.jwsVerifier = new MACVerifier(CommonEnum.SECRETKEY.getValue().getBytes(StandardCharsets.UTF_8));
        this.jwsSigner = new MACSigner(CommonEnum.SECRETKEY.getValue().getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String email, String userRole, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CommonEnum.USER_EMAIL.getValue(), email);
        claims.put(USER_ROLE.getValue(), userRole);
        claims.put(CommonEnum.USER_ID.getValue(), userId);
        return this.generateSignedJwt(email, claims).serialize();
    }

    private SignedJWT generateSignedJwt(String subject, Map<String, Object> claims) {
        try {
            var jwtClaimsSet = verifyClaims(claims);
            Date now = new Date();

            //Validation Set to 15 Min as per standardization ::
            Date validity = new Date(now.getTime() + 8 * 3600 * 1000);


            //Create Header ::
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                    .type(JOSEObjectType.JWT)
                    .build();

            //Create Payload for Token ::
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

    private JWTClaimsSet verifyClaims(Map<String, Object> customClaims) {
        // check if custom claims are present with key and value
        try {
            Map<String, Object> actualClaims = Optional.ofNullable(customClaims)
                    .orElseGet(Collections::emptyMap)
                    .entrySet()
                    .stream()
                    .filter(foundEntry -> List.of(CommonEnum.USER_ID.getValue(), USER_ROLE.getValue()).contains(foundEntry.getKey()))
                    .filter(foundEntry -> Optional.ofNullable(foundEntry.getValue()).isPresent() && Optional.ofNullable(foundEntry.getKey()).isPresent())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return JWTClaimsSet.parse(actualClaims);
        } catch (Exception e) {
            throw new CustomException(JwtExceptionEnum.INVALID_TOKEN.getValue(), HttpStatus.UNAUTHORIZED);
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(CommonEnum.AUTHORIZATION.getValue());
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    //This Method use for Token validation :
    public Boolean validateToken(String token) {
        this.parseJwtAndExtractClaims(token);
        return this.isTokenExpired(token);
    }

    //This Method Use Mostly All Method So create first ::
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

    private Boolean isTokenExpired(String token) {
        if (extractExpiration(token).before(new Date())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private Date extractExpiration(String token) {
        return this.parseJwtAndExtractClaims(token).getExpirationTime();
    }

    // For Authentication of Token ::
    public Authentication getAuthentication(String token) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(getUserRole(token)));
        UserDetails userDetails = new User(getUserName(token), getUserName(token), true, false, false, false, grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Get UserName into Token
    public String getUserName(String token) {
        return this.parseJwtAndExtractClaims(token).getSubject();
    }

    // Get UserId into Token
    public Long getUserIdFromToken(String token) {
        return (Long) this.parseJwtAndExtractClaims(token).getClaim(CommonEnum.USER_ID.getValue());
    }

    // Get UserRole into Token
    public String getUserRole(String token) {
        return (String) this.parseJwtAndExtractClaims(token).getClaim(USER_ROLE.getValue());
    }

    public String createNewTokenFromToken(String token) {
        var jwtClaimsSet = this.parseJwtAndExtractClaims(token);
        var claims = jwtClaimsSet.getClaims();
        return this.generateSignedJwt(jwtClaimsSet.getSubject(), claims).serialize();
    }
}
