package com.safety.security;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {


private JwtTokenProvider tokenProvider;
private CustomUserDetailsService userDetailsService;


public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, CustomUserDetailsService userDetailsService) {
this.tokenProvider = tokenProvider;
this.userDetailsService = userDetailsService;
}


@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, jakarta.servlet.ServletException {
String header = request.getHeader("Authorization");
if (header != null && header.startsWith("Bearer ")) {
String token = header.substring(7);
if (tokenProvider.validateToken(token)) {
String username = tokenProvider.getUsernameFromToken(token);
var user = userDetailsService.loadUserByUsername(username);
// build auth
var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
SecurityContextHolder.getContext().setAuthentication(auth);
}
}
filterChain.doFilter(request, response);
}
}