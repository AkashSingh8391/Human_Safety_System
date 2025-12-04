package com.safety.config;

import com.safety.service.JwtUtil;
import com.safety.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired private JwtUtil jwtUtil;
    @Autowired private MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        String path = req.getServletPath();

        // allow public login/register and websocket handshake without JWT
        if (path.equals("/api/auth/login") || path.equals("/api/auth/register") || path.startsWith("/ws")) {
            chain.doFilter(req, res);
            return;
        }

        final String authHeader = req.getHeader("Authorization");
        String token = null, subject = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try { subject = jwtUtil.extractUsername(token); } catch (Exception e) {}
        }

        if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null && jwtUtil.validateToken(token)) {
            var userDetails = userDetailsService.loadUserByUsername(subject);
            UsernamePasswordAuthenticationToken upa = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            upa.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(upa);
        }
        chain.doFilter(req, res);
    }
}
