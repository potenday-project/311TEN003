package com.bside.bside_311.controller;

import com.bside.bside_311.Bside311Application;
import com.bside.bside_311.config.security.WebSecurityConfig;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {
    Bside311Application.class,
    WebSecurityConfig.class,
})
public abstract class ControllerTest {
  protected static final String USER_ID = "UserId";
  protected static final String ADMIN_ID = "AdminId";
//  @SpyBean
//  protected AccessTokenGenerator accessTokenGenerator;
//  @MockBean
//  protected AuthUserDao authUserDao;
  protected String userAccessToken;
  protected String adminAccessToken;
//  @SpyBean
//  private AccessTokenService accessTokenService;
//
//  @BeforeEach
//  void setUpAccessTokenAndUserDetailsDaoForAuthentication() {
//    userAccessToken = accessTokenGenerator.generate(USER_ID);
//    adminAccessToken = accessTokenGenerator.generate(ADMIN_ID);
//
//    AuthUser user = AuthUser.authenticated(
//        USER_ID, "ROLE_USER", userAccessToken);
//
//    given(authUserDao.findByAccessToken(userAccessToken))
//        .willReturn(Optional.of(user));
//
//    AuthUser admin = AuthUser.authenticated(
//        ADMIN_ID, "ROLE_ADMIN", adminAccessToken);
//
//    given(authUserDao.findByAccessToken(adminAccessToken))
//        .willReturn(Optional.of(admin));
//  }
}
