package com.bitsvalley.micro.config;

import com.bitsvalley.micro.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = (String) authentication.getCredentials();

    UserDetails user = userDetailsService.loadUserByUsername(username);

    if (user == null) {
      throw new BadCredentialsException("User not found");
    }

    if(password.equals(user.getPassword())){
      return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
    }else if(!user.isAccountNonLocked()){
      throw new BadCredentialsException("Account locked");
    }else if(passwordEncoder.matches(password, user.getPassword())){
      return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
    }else{
      throw new BadCredentialsException("Incorrect password");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}

