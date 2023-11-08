package com.bside.bside_311.util;

import com.bside.bside_311.dto.AuthDto;
import com.bside.bside_311.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
  public final String TOKEN_TYPE = "tokenType";
  public final String USER_NO = "userNo";
  public final String USER_ID = "userId";

  public String createLocalToken(User user, String tokenType, Long validity, Authentication authentication) {
    String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                       .collect(Collectors.joining(","));

    Claims claims = Jwts.claims();
    claims.put(TOKEN_TYPE, tokenType);

    claims.put(USER_NO, user.getUserNo());
    claims.put(USER_ID, user.getId());
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

  public Authentication getAuthentication(String token) {
    AuthDto authDto = getAuthToken(token);
    return new UsernamePasswordAuthenticationToken(authDto.getUserNo(), "", authDto.getAuthorities());
  }

  private AuthDto getAuthToken(String token) {
    Claims claims = getClaims(token);
    String[] roles = claims.get(AUTHORITIES_KEY).toString().split(",");
    List<SimpleGrantedAuthority>
        authorities = Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    return AuthDto.builder()
                            .tokenType(String.valueOf(claims.get(TOKEN_TYPE, String.class)))
                            .userId(String.valueOf(claims.get(USER_ID, String.class)))
                            .userNo(claims.get(USER_NO, Integer.class))
                            .authorities(authorities)
                            .build();
  }

  public static String getTokenFromRequest(HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    return token == null ? null : token.substring(BEARER_PREFIX.length());
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      log.info("AuthorizationException - {}", e.getMessage());
      throw new AuthenticationServiceException("유효하지 않은 토큰입니다.");
    } catch (ExpiredJwtException e) {
      log.info("ExpiredJwtException - {}", e.getMessage());
      throw new ExpiredJwtException(getHeader(token), getClaims(token), "이미 만료된 토큰입니다");
    }
  }
  private Header getHeader(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getHeader();
  }

  private Claims getClaims(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }
}
