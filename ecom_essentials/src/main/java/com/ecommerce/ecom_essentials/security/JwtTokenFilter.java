package com.ecommerce.ecom_essentials.security;

import com.ecommerce.ecom_essentials.enums.ExceptionEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);
        System.out.println("token in Filter = " + token);

        if (token != null && !token.isEmpty()) {
            Boolean isTokenValid = jwtTokenProvider.validateToken(token);
            if (!isTokenValid) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ExceptionEnum.INVALID_TOKEN.getValue());
                throw new AccessDeniedException(ExceptionEnum.INVALID_TOKEN.getValue());
            }
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            System.out.println("Authentication Token Filter ::::::::"+authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            setHeader(response,jwtTokenProvider.createNewTokenFromToken(token));
        }
        filterChain.doFilter(request,response);
    }

    private void setHeader(HttpServletResponse response, String newToken) {
        response.setHeader("Authorization", newToken);
        response.setHeader("traceId", MDC.get("traceId"));
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
    }

}
