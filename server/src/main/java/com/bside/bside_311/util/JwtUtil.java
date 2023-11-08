package com.bside.bside_311.util;

import com.bside.bside_311.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {
  public static final String AUTHORITIES_KEY = "authorities";
  public static final String NORMAL_TOKEN = "NORMAL_TOKEN";
  public static final String SOCIAL_TOKEN = "SOCIAL_TOKEN";
  public static final String REMEMBER_TOKEN = "REMEMBER_TOKEN";
  public static final String BEARER_PREFIX = "Bearer ";
  public static final long normalValidity = 1000 * 36000; // 10시간
  public final String secretKey = "bside311secret";


  public String createLocalToken(User user, String tokenType, Long validity, Authentication authentication) {
    String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                       .collect(Collectors.joining(","));

    Claims claims = Jwts.claims();

    claims.put("tokenType", tokenType);
    claims.put("userNo", user.getUserNo());
    claims.put("id", user.getId());
    claims.put(AUTHORITIES_KEY, authorities);

    Date now = new Date();
    Date expiration = new Date(now.getTime() + validity);

    return Jwts.builder()
               .setSubject(String.format("%d_%s", user.getUserNo(), user.getId()))
               .setIssuedAt(now)
               .setClaims(claims)
               .setExpiration(expiration)
               .signWith(SignatureAlgorithm.HS512, secretKey)
               .compact();
  }
}
