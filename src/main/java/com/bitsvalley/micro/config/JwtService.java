package com.bitsvalley.micro.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.Base64Codec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

  public Claims extractClaims(String token) {
    return getClaims(token);
  }
  public Claims getClaims(String token){
    // Split the token into parts
    String[] parts = token.split("\\.");
    if (parts.length != 3) {
      throw new IllegalArgumentException("Invalid token format");
    }
    // Decode the payload
    String payload = new String(Base64Codec.BASE64URL.decode(parts[1]));
    // Parse the payload as Claims
    return Jwts.parser()
        .parseClaimsJwt(parts[0] + "." + parts[1] + ".")
        .getBody();
  }

}
