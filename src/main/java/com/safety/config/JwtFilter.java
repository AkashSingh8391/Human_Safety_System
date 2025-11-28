package com.safety.config;

import com.safety.service.JwtUtil;
import com.safety.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired private JwtUtil jwtUtil;
    @Autowired private MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String path = req.getServletPath();
        if (path.startsWith("/api/auth")) {
            chain.doFilter(req, res);
            return;
        }

        final String authHeader = req.getHeader("Authorization");
        String token = null, username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try { username = jwtUtil.extractUsername(token); } catch (Exception e) { /* invalid token */ }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && jwtUtil.validateToken(token)) {
            var userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            upa.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(upa);
        }

        chain.doFilter(req, res);
    }
}
