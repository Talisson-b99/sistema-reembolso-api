package com.barbosa.sistema_reembolso.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    private TokenUtil tokenUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if(request.getHeader("Authorization") != null && request.getHeader("Authorization").startsWith("Bearer ")){
        Authentication auth = tokenUtil.decode(request);
        if(auth != null) {
            System.out.println("DEBUG - setando authentication");
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }
    filterChain.doFilter(request, response);
    }
}
