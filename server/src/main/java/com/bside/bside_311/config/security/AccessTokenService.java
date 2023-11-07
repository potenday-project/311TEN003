package com.bside.bside_311.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessTokenService {
//  private final AccessTokenGenerator accessTokenGenerator;
//  private final AuthUserDao authUserDao;
//
//  public AccessTokenService(AccessTokenGenerator accessTokenGenerator,
//                            AuthUserDao authUserDao) {
//    this.accessTokenGenerator = accessTokenGenerator;
//    this.authUserDao = authUserDao;
//  }
//
//  public Authentication authenticate(String accessToken) {
//    if (!accessTokenGenerator.verify(accessToken)) {
//      return null;
//    }
//
//    return authUserDao.findByAccessToken(accessToken)
//                      .map(authUser ->
//                          UsernamePasswordAuthenticationToken.authenticated(
//                              // Principal로 AuthUser 객체를 그대로 활용한다.
//                              authUser, null, List.of(authUser::role)))
//                      .orElse(null);
//  }
}
