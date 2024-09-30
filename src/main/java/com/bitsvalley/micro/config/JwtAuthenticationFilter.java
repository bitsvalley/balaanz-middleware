package com.bitsvalley.micro.config;

import com.bitsvalley.micro.domain.User;
import com.bitsvalley.micro.domain.UserRole;
import com.bitsvalley.micro.services.UserService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserService userDetailsService;
  public JwtAuthenticationFilter(JwtService jwtService, AuthenticationManager authenticationManager,
                                 UserService userDetailsService) {
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    return !path.contains("/api/");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    if(StringUtils.isBlank(authHeader)){
      throw new AuthenticationException("Access Denied");
    }

    if (authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      try {
        Claims claims = jwtService.extractClaims(token);
        Object user = claims.get("user");
        User user1 = userDetailsService.getUserById(Long.parseLong(String.valueOf(user)))
          .orElseThrow(() -> new AuthenticationException("Access denied"));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
          user1.getUserName(), null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
      } catch (Exception e) {
        // Handle invalid token
        throw new AuthenticationException("Access denied");
      }
    }
    if (authHeader.startsWith("Basic ")) {
      String base64Credentials = authHeader.substring("Basic".length()).trim();
      String credentials = new String(Base64.getDecoder().decode(base64Credentials));
      final String[] values = credentials.split(":", 2);
      if (values.length == 2) {
        String username = values[0];
        String password = values[1];
        UsernamePasswordAuthenticationToken authRequest =
            new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }

    filterChain.doFilter(request, response);
  }
}
