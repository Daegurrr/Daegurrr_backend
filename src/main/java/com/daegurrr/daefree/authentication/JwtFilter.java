package com.daegurrr.daefree.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private JwtManager jwtManager;
    private static final Collection<GrantedAuthority> collection = new ArrayList<>();
    static {
        collection.add((GrantedAuthority) () -> "ROLE_USER");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String jwtHeader = request.getHeader("Authorization");

        if (jwtHeader==null){
            request.setAttribute("exception", "엑세스 토큰이 존재하지 않습니다.");
            chain.doFilter(request,response);
            return;
        }

        try {
            Claims claims = jwtManager.validateToken(jwtHeader);
            Long id = Long.valueOf(claims.getSubject());

            JwtDetails jwtDetails = new JwtDetails(id);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(jwtDetails, null, collection);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (RuntimeException e) {
            request.setAttribute("exception", e.getMessage());
        }

        chain.doFilter(request,response);

    }
}
