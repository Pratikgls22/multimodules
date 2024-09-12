package com.ecommerce.security.security.jwt;

import com.ecommerce.utility.enums.ExceptionEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);

        if ((token != null && !token.isEmpty())) {
            Boolean isTokenValid = jwtTokenProvider.validateToken(token);
            if (!isTokenValid) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ExceptionEnum.INVALID_TOKEN.getValue());
                throw new AccessDeniedException(ExceptionEnum.INVALID_TOKEN.getValue());
            }
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            System.out.println("authentication TOKeN FILTER==================== " + authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            setHeader(response, jwtTokenProvider.createNewTokenFromToken(token));
        }
        filterChain.doFilter(request, response);
    }

    private void setHeader(HttpServletResponse response, String newToken) {
        response.setHeader("Authorization", newToken);
        response.setHeader("traceId", MDC.get("traceId"));
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
    }
}
