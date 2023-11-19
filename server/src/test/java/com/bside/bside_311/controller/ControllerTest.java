package com.bside.bside_311.controller;

import com.bside.bside_311.Bside311Application;
import com.bside.bside_311.config.security.WebSecurityConfig;
import com.bside.bside_311.entity.Role;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.ContextConfiguration;

import static com.bside.bside_311.util.JwtUtil.NORMAL_TOKEN;
import static com.bside.bside_311.util.JwtUtil.normalValidity;

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
  public User normalUser;
  @SpyBean
  protected JwtUtil jwtUtil;
  protected String userAccessToken;
  protected String adminAccessToken;


  //  @SpyBean
//  private AccessTokenService accessTokenService;
//
  @BeforeEach
  void setUpAccessTokenAndUserDetailsDaoForAuthentication() {
    normalUser = User.builder()
                     .id(1L)
                     .userId("normalUser")
                     .password("normalUserPassword")
                     .email("normalUser@example.com")
                     .nickname("normalUserNickname")
                     .role(Role.ROLE_USER)
                     .introduction("normalUserIntroduction")
                     .build();
    Authentication normalUserAuthentication
        = new UsernamePasswordAuthenticationToken(normalUser.getId(),
        null,
        AuthorityUtils.createAuthorityList(normalUser.getRole().toString()));

    userAccessToken = jwtUtil.createLocalToken(normalUser, NORMAL_TOKEN, normalValidity,
        normalUserAuthentication);


    User adminUser = User.builder()
                         .id(2L)
                         .userId("adminUser")
                         .password("adminUserPassword")
                         .email("adminUser@example.com")
                         .nickname("adminUserNickname")
                         .role(Role.ROLE_USER)
                         .introduction("adminUserIntroduction")
                         .build();
    Authentication adminUserAuthentication
        = new UsernamePasswordAuthenticationToken(adminUser.getId(),
        null,
        AuthorityUtils.createAuthorityList(adminUser.getRole().toString()));

    adminAccessToken =
        jwtUtil.createLocalToken(adminUser, NORMAL_TOKEN, normalValidity, adminUserAuthentication);
  }
}
